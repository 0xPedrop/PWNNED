package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.SignupDTO;
import com.pwnned.domain.model.User;
import com.pwnned.port.input.UserServicePort;
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
    public ResponseEntity<String> signup(@RequestBody SignupDTO signupDTO) {
        User user = new User();
        user.setEmail(signupDTO.email());
        user.setUsername(signupDTO.username());
        user.setPassword(signupDTO.password());

        userServicePort.createUser(user);

        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }
}
