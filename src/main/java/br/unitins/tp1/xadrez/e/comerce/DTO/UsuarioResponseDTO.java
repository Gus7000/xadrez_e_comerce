package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;

public record UsuarioResponseDTO(
        Long id,
        @Email
        String email,
        String keycloakId,
        LocalDateTime dataCadastro) {
}
