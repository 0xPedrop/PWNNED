package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import com.pwnned.port.output.UserRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user, new CycleAvoidingMappingContext());
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.toModel(savedUser, new CycleAvoidingMappingContext());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(entity -> userMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId)
                .map(entity -> userMapper.toModel(entity, new CycleAvoidingMappingContext()));
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
        return userRepository.findByUserType(userType).stream()
                .map(entity -> userMapper.toModel(entity, new CycleAvoidingMappingContext()))
                .toList();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(entity -> userMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Integer getUserExperiencePoints(UUID userId) {
        return userRepository.getUserExperiencePoints(userId);
    }
}