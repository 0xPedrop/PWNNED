package com.pwnned.port.output;

import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(UUID userId);
    Optional<User> findByUsername(String username);
    void deleteById(UUID userId);
    void deleteAll();
    List<User> getUsersByType(UserType userType);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
