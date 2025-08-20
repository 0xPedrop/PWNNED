package com.pwnned.port.input;

import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServicePort {
    User createUser(User user);
    Page<User> getAllUsers(Pageable pageable);
    Optional<User> authenticateUser(String username, String password);
    User getSingleUser(UUID userId);
    void deleteUser(UUID userId);
    void deleteAllUsers(Pageable pageable);
    void promoteUser(UUID userId);
    List<User> getUsersByType(UserType userType);
}
