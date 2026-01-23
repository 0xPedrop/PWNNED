package com.pwnned.adapter.output.jpa.repository;

import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.adapter.input.mapper.util.CycleAvoidingMappingContext;
import com.pwnned.adapter.output.jpa.repository.entity.UserEntity;
import com.pwnned.adapter.output.jpa.repository.util.SnowflakeIdGenerator;
import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import com.pwnned.port.output.UserRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SnowflakeIdGenerator idGenerator;

    public UserRepositoryAdapter(UserRepository userRepository, UserMapper userMapper, SnowflakeIdGenerator idGenerator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.idGenerator = idGenerator;
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user, new CycleAvoidingMappingContext());
        if (entity.getUserId() == null) {
            entity.setUserId(idGenerator.nextId());
        }
        return userMapper.toModel(userRepository.save(entity), new CycleAvoidingMappingContext());
    }

    @Override public Optional<User> findById(Long userId) {
        return userRepository.findById(userId).map(e -> userMapper.toModel(e, new CycleAvoidingMappingContext()));
    }
    @Override public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(e -> userMapper.toModel(e, new CycleAvoidingMappingContext()));
    }
    @Override public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(e -> userMapper.toModel(e,
                new CycleAvoidingMappingContext()));
    }
    @Override public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(entity -> userMapper.toModel(entity, new CycleAvoidingMappingContext()));
    }
    @Override public void deleteById(Long userId) { userRepository.deleteById(userId); }
    @Override public void deleteAll() { userRepository.deleteAll(); }
    @Override public List<User> getUsersByType(UserType userType) {
        return userRepository.findByUserType(userType).stream().map(e -> userMapper.toModel(e,
                new CycleAvoidingMappingContext())).toList();
    }
    @Override public boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }
    @Override public boolean existsByUsername(String username) { return userRepository.existsByUsername(username); }
    @Override public Integer getUserExperiencePoints(Long userId) { return userRepository.getUserExperiencePoints(userId); }
}