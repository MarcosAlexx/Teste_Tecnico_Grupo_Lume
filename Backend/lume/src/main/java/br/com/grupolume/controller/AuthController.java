package br.com.grupolume.controller;

import br.com.grupolume.dto.LoginRequestDTO;
import br.com.grupolume.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Realizar login e obter tokens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                    {
                      "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBsdW1lLmNvbSJ9...",
                      "refreshToken": "8dc2bffe-fcd2-4603-9d8a-4c49101e0d7f"
                    }
                    """))),
            @ApiResponse(responseCode = "401", description = "Email ou senha inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Usuário não encontrado", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        var tokens = authService.login(body.email(), body.password());
        return ResponseEntity.ok(tokens);
    }
}