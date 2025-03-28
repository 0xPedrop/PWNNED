package com.pwnned.port.input;

import com.pwnned.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServicePort {
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> getSingleUser(Long userId);
    void deleteUser(Long userId);
    void deleteAllUsers();
}
