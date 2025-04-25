package com.pwnned.adapter.input.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pwnned.adapter.input.dto.LoginDTO;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserServicePort;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final UserServicePort userServicePort;

    public LoginController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<User> userOptional = userServicePort.authenticateUser(loginDTO.username(), loginDTO.password());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(Map.of(
                "id", user.getUserId(),
                "username", user.getUsername(),
                "email", user.getEmail() 
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciais inválidas")); 
        }
    }
}
