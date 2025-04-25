package com.pwnned.port.input;

import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServicePort {
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> authenticateUser(String username, String password);
    Optional<User> getSingleUser(UUID userId);
    void deleteUser(UUID userId);
    void deleteAllUsers();
    void promoteUser(UUID userId);
    List<User> getUsersByType(UserType userType);
}
