package com.pwnned.adapter.input.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pwnned.adapter.input.dto.PageableDTO;
import com.pwnned.adapter.input.dto.UserDTO;
import com.pwnned.adapter.input.mapper.PageableMapper;
import com.pwnned.adapter.input.mapper.UserMapper;
import com.pwnned.domain.enums.UserType;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserControllerPort;
import com.pwnned.port.input.UserServicePort;
import com.pwnned.port.output.StorageRepositoryPort;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/users")
@RestController
public class UserController implements UserControllerPort {
    private final UserServicePort userServicePort;
    private final UserMapper userMapper;
    private final StorageRepositoryPort storageRepositoryPort;
    private final PageableMapper pageableMapper;

    public UserController(UserServicePort userServicePort, UserMapper userMapper,
                          StorageRepositoryPort storageRepositoryPort, PageableMapper pageableMapper) {
        this.userServicePort = userServicePort;
        this.userMapper = userMapper;
        this.storageRepositoryPort = storageRepositoryPort;
        this.pageableMapper = pageableMapper;
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
        return ResponseEntity.ok(pageableMapper.toDTO(usersDTO));
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Long userId) {
        User user = userServicePort.getSingleUser(userId);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
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
    public ResponseEntity<String> promoteUser(@PathVariable Long userId) {
        userServicePort.promoteUser(userId);
        return ResponseEntity.ok("User " + userId + " is a Premium User Now");
    }

    @Override
    @GetMapping("/type/{userType}")
    public ResponseEntity<List<UserDTO>> getUsersByType(@PathVariable UserType userType) {
        List<UserDTO> usersDTO = userServicePort.getUsersByType(userType)
                .stream()
                .map(userMapper::toDTO)
                .toList();
        return ResponseEntity.ok(usersDTO);
    }

    @Override
    @PostMapping("/{userId}/upload-photo")
    public ResponseEntity<String> uploadPhoto(@PathVariable String userId, @RequestParam("file") MultipartFile file)
            throws Exception {
        String fileName = userId + "_" + file.getOriginalFilename();
        String result = storageRepositoryPort.uploadFile(fileName, file.getInputStream(), file.getContentType());
        return ResponseEntity.ok("Arquivo enviado com sucesso: " + result);
    }
}
