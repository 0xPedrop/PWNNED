package com.pwnned.adapter.input.controller;

import org.springframework.security.core.Authentication;
import com.pwnned.adapter.input.dto.AuthResponseDTO;
import com.pwnned.adapter.input.dto.LoginDTO;
import com.pwnned.domain.model.User;
import com.pwnned.infra.security.TokenService;
import com.pwnned.port.input.UserServicePort;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserServicePort userService;
    private final TokenService tokenService;

    // Ajuste este valor conforme o application.properties (86400 seg = 1 dia) (7200 seg = 2h)
    private final long EXPIRATION_SECONDS = 7200; 

    public AuthController(UserServicePort userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        User user = userService.authenticateUser(loginDTO.email(), loginDTO.password())
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        String token = tokenService.generateToken(user);

        
        ResponseCookie cookie = ResponseCookie.from("pwnned_token", token)
                .httpOnly(true)       // JS não lê (Proteção XSS)
                .secure(false)        // Setar 'true' em produção (HTTPS)
                .path("/")           
                .maxAge(EXPIRATION_SECONDS)
                .sameSite("Lax")      
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new AuthResponseDTO("Login realizado com sucesso via Cookie"));
    }
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok("Valid Session: " + authentication.getName());
        }
        return ResponseEntity.status(401).build();
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Sobrescreve o cookie com validade 0
        ResponseCookie cookie = ResponseCookie.from("pwnned_token", "")
                .httpOnly(true)
                .secure(false) // true em produção (HTTPS)
                .path("/")
                .maxAge(0) // Expira imediatamente
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().build();
    }
}