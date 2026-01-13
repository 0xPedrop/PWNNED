package com.pwnned.domain.service;

import com.pwnned.adapter.output.redis.UserRedisAdapter;
import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.exception.UserAlreadyExistsException;
import com.pwnned.domain.exception.UserAlreadyPremiumException;
import com.pwnned.domain.exception.UserNotFoundException;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserLogServicePort;
import com.pwnned.port.input.UserServicePort;
import com.pwnned.port.output.CertificateRepositoryPort;
import com.pwnned.port.output.UserRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;
    private final UserRedisAdapter userRedisAdapter;
    private final CertificateRepositoryPort certificateRepositoryPort;
    private final UserLogServicePort userLogServicePort;


    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepositoryPort userRepositoryPort, UserRedisAdapter userRedisAdapter,
                       CertificateRepositoryPort certificateRepositoryPort, UserLogServicePort userLogServicePort,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.userRedisAdapter = userRedisAdapter;
        this.certificateRepositoryPort = certificateRepositoryPort;
        this.userLogServicePort = userLogServicePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        if (userRepositoryPort.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email já está em uso");
        } 
        
        if (userRepositoryPort.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username já está em uso");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserType(UserType.BASIC);
        User createdUser = userRepositoryPort.save(user);
        userLogServicePort.logAction(createdUser.getUserId().toString(), "USUÁRIO CRIADO");
        return createdUser;
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepositoryPort.findAll(pageable);
    }

    @Override
    public User getSingleUser(UUID userId) {
        Optional<User> cachedUser = userRedisAdapter.getCachedUser(userId);
        if(cachedUser.isPresent()) {
            return cachedUser.get();
        }

        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        userRedisAdapter.cacheUser(user);
        return user;
    }

    @Override
    public void deleteUser(UUID userId) {
        Optional<User> user = userRepositoryPort.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException("User " + userId + " Not Found");
        userRepositoryPort.deleteById(userId);

        userRedisAdapter.invalidateCacheForUser(userId);
        userRedisAdapter.invalidateCacheForUsersByType(user.get().getUserType().name());
    }

    @Override
    public void deleteAllUsers(Pageable pageable) {
        Page<User> users = userRepositoryPort.findAll(pageable);
        users.forEach(user -> certificateRepositoryPort.deleteAllByUserId(user.getUserId()));

        userRepositoryPort.deleteAll();
        userRedisAdapter.invalidateCacheForUsersByType(UserType.BASIC.name());
        userRedisAdapter.invalidateCacheForUsersByType(UserType.PREMIUM.name());
    }

    @Override
    public void promoteUser(UUID userId) {
        Optional<User> searchedUser = userRepositoryPort.findById(userId);
        if (searchedUser.isEmpty()) throw new UserNotFoundException("User " + userId + " Not Found");
        User user = searchedUser.get();
        if (user.getUserType().equals(UserType.PREMIUM)) {
            throw new UserAlreadyPremiumException("User is Already Premium");
        }

        userRedisAdapter.invalidateCacheForUsersByType(user.getUserType().name());

        user.setUserType(UserType.PREMIUM);
        userRepositoryPort.save(user);

        userRedisAdapter.invalidateCacheForUsersByType(user.getUserType().name());
        userRedisAdapter.invalidateCacheForUser(userId);
    }

    @Override
    public List<User> getUsersByType(UserType userType) {
        Optional<List<User>> cachedUsersByType = userRedisAdapter.getCachedUsersByType(userType.name());

        if (cachedUsersByType.isPresent()) {
            return cachedUsersByType.get();
        }

        List<User> users = userRepositoryPort.getUsersByType(userType);
        if (users.isEmpty()) throw new UserNotFoundException("Users Not Found");

        userRedisAdapter.cacheUsersByType(userType.name(), users);
        return users;
    }

    @Override
    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> user = userRepositoryPort.findByUsername(username);
        if (user.isEmpty()) {
            return Optional.empty(); 
        }
        User foundUser = user.get();
        if (passwordEncoder.matches(password, foundUser.getPassword())) {
            return Optional.of(foundUser); 
        } else {
            return Optional.empty(); 
        }
    }
}
