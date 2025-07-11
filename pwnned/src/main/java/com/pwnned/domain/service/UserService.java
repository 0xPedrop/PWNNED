package com.pwnned.domain.service;

import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.exception.UserAlreadyExistsException;
import com.pwnned.domain.exception.UserAlreadyPremiumException;
import com.pwnned.domain.exception.UserNotFoundException;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserServicePort;
import com.pwnned.port.output.UserRepositoryPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepositoryPort userRepositoryPort, BCryptPasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        if (userRepositoryPort.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email já está em uso");
        } 
        
        if (userRepositoryPort.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username já está em uso");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserType(UserType.BASIC);
        return userRepositoryPort.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositoryPort.findAll();
    }

    @Override
    public User getSingleUser(UUID userId) {
        return userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public void deleteUser(UUID userId) {
        Optional<User> user = userRepositoryPort.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException("User " + userId + " Not Found");
        userRepositoryPort.deleteById(userId);
    }

    @Override
    public void deleteAllUsers() {
        List<User> users = userRepositoryPort.findAll();
        if (users.isEmpty()) throw new UserNotFoundException("No Users to Delete");
        userRepositoryPort.deleteAll();
    }

    @Override
    public void promoteUser(UUID userId) {
        Optional<User> searchedUser = userRepositoryPort.findById(userId);
        if (searchedUser.isEmpty()) throw new UserNotFoundException("User " + userId + " Not Found");
        User user = searchedUser.get();
        if (user.getUserType().equals(UserType.PREMIUM)) {
            throw new UserAlreadyPremiumException("User is Already Premium");
        }
        user.setUserType(UserType.PREMIUM);
        userRepositoryPort.save(user);
    }

    @Override
    public List<User> getUsersByType(UserType userType) {
        List<User> users = userRepositoryPort.getUsersByType(userType);
        if (users.isEmpty()) throw new UserNotFoundException("Users Not Found");
        return users;
    }

    @Override
    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> user = userRepositoryPort.findByUsername(username);
        if (user.isEmpty()) {
            return Optional.empty(); 
        }
        User foundUser = user.get();
        if (passwordEncoder.matches(password, foundUser.getPassword())) {
            return Optional.of(foundUser); 
        } else {
            return Optional.empty(); 
        }
    }
}
