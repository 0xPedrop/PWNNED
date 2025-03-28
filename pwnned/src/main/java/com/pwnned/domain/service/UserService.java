package com.pwnned.domain.service;

import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserServicePort;
import com.pwnned.port.output.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return userRepositoryPort.findAll();
    }

    @Override
    public Optional<User> getSingleUser(Long userId) {
        return userRepositoryPort.findById(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepositoryPort.deleteById(userId);
    }

    @Override
    public void deleteAllUsers() {
        userRepositoryPort.deleteAll();
    }
}
