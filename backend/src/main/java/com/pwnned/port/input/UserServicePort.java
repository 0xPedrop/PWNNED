package com.pwnned.port.input;

import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserServicePort {
    User createUser(User user);
    Page<User> getAllUsers(Pageable pageable);
    Optional<User> authenticateUser(String username, String password);
    User getSingleUser(Long userId);
    void deleteUser(Long userId);
    void deleteAllUsers(Pageable pageable);
    void promoteUser(Long userId);
    List<User> getUsersByType(UserType userType);
    String uploadUserProfilePicture(Long userId, MultipartFile file) throws Exception;
    void deleteUserProfilePicture(Long userId);
}