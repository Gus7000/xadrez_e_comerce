package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
        @NotBlank(message = "O email é obrigatório")
        @Email
        String email,
        String keycloakId) {
}
