package br.unitins.tp1.xadrez.e.comerce.DTO;

import br.unitins.tp1.xadrez.e.comerce.model.Perfil;
import jakarta.validation.constraints.Email;

public record AuthResponseDTO(
        @Email
        String email,
        String token,
        Perfil perfil) {
}