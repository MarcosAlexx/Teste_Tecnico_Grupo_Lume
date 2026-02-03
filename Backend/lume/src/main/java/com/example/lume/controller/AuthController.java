package com.example.lume.controller;

import com.example.lume.repository.UserRepository;
import com.example.lume.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        var user = userRepository.findByEmail(body.get("email"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!body.get("password").equals(user.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        var token = tokenService.gerarAccessToken(user);
        var refresh = tokenService.gerarRefreshToken(user);

        return ResponseEntity.ok(Map.of(
                "accessToken", token,
                "refreshToken", refresh.getToken()
        ));
    }
}