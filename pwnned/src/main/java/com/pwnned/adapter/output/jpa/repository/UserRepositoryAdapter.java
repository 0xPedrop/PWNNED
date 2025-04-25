package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import com.pwnned.port.output.UserRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;

    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(user);
        UserEntity savedUser = userRepository.save(userEntity);
        return UserMapper.INSTANCE.toModel(savedUser);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map(UserMapper.INSTANCE::toModel).toList();
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId).map(UserMapper.INSTANCE::toModel);
    }

    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public List<User> getUsersByType(UserType userType) {
        return userRepository.findByUserType(userType).stream().map(UserMapper.INSTANCE::toModel).toList();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(UserMapper.INSTANCE::toModel);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
