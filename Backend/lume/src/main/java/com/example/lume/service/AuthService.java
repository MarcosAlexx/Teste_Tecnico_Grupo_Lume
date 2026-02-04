package com.example.lume.service;

import com.example.lume.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public Map<String, String> login(String email, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        var token = tokenService.gerarAccessToken(user);
        var refresh = tokenService.gerarRefreshToken(user);

        return Map.of(
                "accessToken", token,
                "refreshToken", refresh.getToken()
        );
    }
}
