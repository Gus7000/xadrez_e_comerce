package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.Email;

public record MeResponseDTO(
        @Email
        String email,
        String perfil) {
}