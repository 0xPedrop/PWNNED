package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserControllerPort;
import com.pwnned.port.input.UserServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
@RestController
public class UserController implements UserControllerPort {
    private final UserServicePort userServicePort;

    public UserController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toModel(userDTO);
        User createdUser = userServicePort.createUser(user);
        UserDTO createdUserDTO = UserMapper.INSTANCE.toDTO(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usersDTO = userServicePort.getAllUsers().stream()
                .map(UserMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(usersDTO);
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable UUID userId) {
        return userServicePort.getSingleUser(userId)
                .map(user -> ResponseEntity.ok(UserMapper.INSTANCE.toDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userServicePort.deleteUser(userId);
        return ResponseEntity.ok("User " + userId + " Deleted");
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers() {
        userServicePort.deleteAllUsers();
        return ResponseEntity.ok("All Users Deleted");
    }

    @Override
    @PutMapping("/{userId}")
    public ResponseEntity<String> promoteUser(@PathVariable UUID userId) {
        userServicePort.promoteUser(userId);
        return ResponseEntity.ok("User " + userId + " is a Premium User Now");
    }

    @Override
    @GetMapping("/type/{userType}")
    public ResponseEntity<List<UserDTO>> getUsersByType(UserType userType) {
        List<UserDTO> usersDTO = userServicePort.getUsersByType(userType)
                .stream()
                .map(UserMapper.INSTANCE::toDTO)
                .toList();
        return ResponseEntity.ok(usersDTO);
    }
}
