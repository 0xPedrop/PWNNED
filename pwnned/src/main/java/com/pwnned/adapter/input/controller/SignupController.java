package com.pwnned.adapter.input.controller;

import com.pwnned.adapter.input.dto.SignupDTO;
import com.pwnned.adapter.input.response.ApiResponse;
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
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupDTO signupDTO) {
        User newUser = new User();
        newUser.setEmail(signupDTO.email());
        newUser.setUsername(signupDTO.username());
        newUser.setPassword(signupDTO.password());

        userServicePort.createUser(newUser);
        return ResponseEntity.ok(new ApiResponse("Usuário cadastrado com sucesso!"));

    }
}
