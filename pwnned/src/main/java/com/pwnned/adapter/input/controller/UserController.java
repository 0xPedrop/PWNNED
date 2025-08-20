package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.PageableDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.input.mapper.PageableMapper;
import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserControllerPort;
import com.pwnned.port.input.UserServicePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/users")
@RestController
public class UserController implements UserControllerPort {
    private final UserServicePort userServicePort;
    private final UserMapper userMapper;

    public UserController(UserServicePort userServicePort, UserMapper userMapper) {
        this.userServicePort = userServicePort;
        this.userMapper = userMapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.toModel(userDTO);
        User createdUser = userServicePort.createUser(user);
        UserDTO createdUserDTO = userMapper.toDTO(createdUser);
        return ResponseEntity.status(201).body(createdUserDTO);
    }

    @Override
    @GetMapping
    public ResponseEntity<PageableDTO> getAllUsers(@PageableDefault(size = 5, sort = "username") Pageable pageable) {
        Page<UserDTO> usersDTO = userServicePort.getAllUsers(pageable)
                .map(userMapper::toDTO);
        return ResponseEntity.ok(PageableMapper.INSTANCE.toDTO(usersDTO));
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable UUID userId) {
        User user = userServicePort.getSingleUser(userId);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userServicePort.deleteUser(userId);
        return ResponseEntity.ok("User " + userId + " Deleted");
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteAllUsers(Pageable pageable) {
        userServicePort.deleteAllUsers(pageable);
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
                .map(userMapper::toDTO)
                .toList();
        return ResponseEntity.ok(usersDTO);
    }
}
