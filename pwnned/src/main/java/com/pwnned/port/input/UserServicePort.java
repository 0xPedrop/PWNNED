package com.pwnned.port.input;

import com.pwnned.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServicePort {
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> getSingleUser(UUID userId);
    void deleteUser(UUID userId);
    void deleteAllUsers();
    void promoveUser(UUID userId);
}
