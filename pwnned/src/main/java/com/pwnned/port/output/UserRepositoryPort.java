package com.pwnned.port.output;

import com.pwnned.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long userId);
    void deleteById(Long userId);
    void deleteAll();
}
