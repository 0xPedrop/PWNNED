package com.pwnned.domain.service;

import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.input.mapper.UserMapper;
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
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;
    private final UserRedisAdapter userRedisAdapter;
    private final CertificateRepositoryPort certificateRepositoryPort;
    private final UserLogServicePort userLogServicePort;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepositoryPort userRepositoryPort, UserRedisAdapter userRedisAdapter,
                       CertificateRepositoryPort certificateRepositoryPort, UserLogServicePort userLogServicePort,
                       UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.userRedisAdapter = userRedisAdapter;
        this.certificateRepositoryPort = certificateRepositoryPort;
        this.userLogServicePort = userLogServicePort;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if (userRepositoryPort.existsByEmail(user.getEmail())) throw new UserAlreadyExistsException("Email já está em uso");
        if (userRepositoryPort.existsByUsername(user.getUsername())) throw new UserAlreadyExistsException("Username já está em uso");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserType(UserType.BASIC);

        User createdUser = userRepositoryPort.save(user);
        userRedisAdapter.invalidateCacheForUsersByType(UserType.BASIC.name());

        userLogServicePort.logAction(String.valueOf(createdUser.getUserId()), "USUÁRIO CRIADO");
        return createdUser;
    }

    @Override
    public User getSingleUser(Long userId) {
        return userRedisAdapter.getCachedUser(userId)
                .orElseGet(() -> {
                    User user = userRepositoryPort.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
                    user.setExperiencePoints(userRepositoryPort.getUserExperiencePoints(userId));
                    userRedisAdapter.cacheUser(user);
                    return user;
                });
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepositoryPort.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        userRepositoryPort.deleteById(userId);
        userRedisAdapter.invalidateCacheForUser(userId);
        userRedisAdapter.invalidateCacheForUsersByType(user.getUserType().name());
    }

    @Override public Page<User> getAllUsers(Pageable pageable) { return userRepositoryPort.findAll(pageable); }
    @Override public Optional<User> authenticateUser(String username, String password) {
        return userRepositoryPort.findByUsername(username).filter(u -> passwordEncoder.matches(password, u.getPassword()));
    }

    @Override
    @Transactional
    public void promoteUser(Long userId) {
        User user = userRepositoryPort.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if (user.getUserType() == UserType.PREMIUM) throw new UserAlreadyPremiumException("Already Premium");

        userRedisAdapter.invalidateCacheForUsersByType(user.getUserType().name());
        user.setUserType(UserType.PREMIUM);
        userRepositoryPort.save(user);
        userRedisAdapter.invalidateCacheForUser(userId);
        userRedisAdapter.invalidateCacheForUsersByType(UserType.PREMIUM.name());
    }

    @Override
    public List<User> getUsersByType(UserType userType) {
        return userRedisAdapter.getCachedUsersByType(userType.name())
                .orElseGet(() -> {
                    List<User> users = userRepositoryPort.getUsersByType(userType);
                    userRedisAdapter.cacheUsersByType(userType.name(), users);
                    return users;
                });
    }

    @Override
    @Transactional
    public void deleteAllUsers(Pageable pageable) {
        userRepositoryPort.findAll(pageable).forEach(u -> certificateRepositoryPort.deleteAllByUserId(u.getUserId()));
        userRepositoryPort.deleteAll();
        userRedisAdapter.invalidateAllUsersCache();
    }
}