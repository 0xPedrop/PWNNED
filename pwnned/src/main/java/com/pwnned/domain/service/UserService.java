package com.pwnned.domain.service;

import com.pwnned.domain.exception.UserNotFoundException;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserServicePort;
import com.pwnned.port.output.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserServicePort {
    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User createUser(User user) {
        System.out.println("Creating user: " + user);
        return userRepositoryPort.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepositoryPort.findAll();
        if (users.isEmpty()) throw new UserNotFoundException("Users Not Found");
        return users;
    }

    @Override
    public Optional<User> getSingleUser(UUID userId) {
        Optional<User> user = userRepositoryPort.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException("User " + userId + " Not Found");
        return user;
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
}
