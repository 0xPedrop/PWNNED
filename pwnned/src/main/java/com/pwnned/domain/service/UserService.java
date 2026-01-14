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
import java.util.UUID;

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
        userRedisAdapter.invalidateCacheForUsersByType(UserType.BASIC.name());

        userLogServicePort.logAction(createdUser.getUserId().toString(), "USUÁRIO CRIADO");
        return createdUser;
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepositoryPort.findAll(pageable);
    }

    @Override
    public User getSingleUser(UUID userId) {
        return userRedisAdapter.getCachedUser(userId)
                .orElseGet(() -> {
                    User user = userRepositoryPort.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

                    Integer xp = userRepositoryPort.getUserExperiencePoints(userId);
                    user.setExperiencePoints(xp != null ? xp : 0);

                    userRedisAdapter.cacheUser(user);
                    return user;
                });
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " Not Found"));

        String typeName = user.getUserType().name();

        userRepositoryPort.deleteById(userId);
        userRedisAdapter.invalidateCacheForUser(userId);
        userRedisAdapter.invalidateCacheForUsersByType(typeName);
    }

    @Override
    @Transactional
    public void deleteAllUsers(Pageable pageable) {
        Page<User> users = userRepositoryPort.findAll(pageable);
        users.forEach(user -> certificateRepositoryPort.deleteAllByUserId(user.getUserId()));
        userRepositoryPort.deleteAll();
        userRedisAdapter.invalidateAllUsersCache();
    }

    @Override
    @Transactional
    public void promoteUser(UUID userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " Not Found"));

        if (user.getUserType().equals(UserType.PREMIUM)) {
            throw new UserAlreadyPremiumException("User is Already Premium");
        }

        userRedisAdapter.invalidateCacheForUsersByType(user.getUserType().name());

        user.setUserType(UserType.PREMIUM);
        userRepositoryPort.save(user);

        userRedisAdapter.invalidateCacheForUsersByType(UserType.PREMIUM.name());
        userRedisAdapter.invalidateCacheForUser(userId);
    }

    @Override
    public List<User> getUsersByType(UserType userType) {
        return userRedisAdapter.getCachedUsersByType(userType.name())
                .orElseGet(() -> {
                    List<User> users = userRepositoryPort.getUsersByType(userType);
                    if (!users.isEmpty()) {
                        userRedisAdapter.cacheUsersByType(userType.name(), users);
                    }
                    return users;
                });
    }

    @Override
    public Optional<User> authenticateUser(String username, String password) {
        return userRepositoryPort.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    public UserDTO findUserById(UUID userId) {
        User user = getSingleUser(userId);
        return userMapper.toDTO(user);
    }
}