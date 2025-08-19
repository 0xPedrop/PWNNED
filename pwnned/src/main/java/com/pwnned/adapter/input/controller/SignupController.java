package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.SignupDTO;
import com.pwnned.adapter.input.response.ApiResponse;
import com.pwnned.domain.exception.UserAlreadyExistsException;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserServicePort;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SignupController {

    private final UserServicePort userServicePort;

    public SignupController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDTO signupDTO) {
        try {
            User user = new User();
            user.setEmail(signupDTO.email());
            user.setUsername(signupDTO.username());
            user.setPassword(signupDTO.password());

            userServicePort.createUser(user);

            ApiResponse response = new ApiResponse("Usuário cadastrado com sucesso");
            return ResponseEntity.ok(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }
}
