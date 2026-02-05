package br.com.grupolume.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Min(value = 3, message = "A senha deve conter entre 3 e 6 caracteres")
        @Max(value = 6, message = "A senha deve conter entre 3 e 6 caracteres")
        String password
) {}