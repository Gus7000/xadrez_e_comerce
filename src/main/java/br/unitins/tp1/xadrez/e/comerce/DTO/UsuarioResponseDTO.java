package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

import br.unitins.tp1.xadrez.e.comerce.model.Perfil;
import jakarta.validation.constraints.Email;

public record UsuarioResponseDTO(
        Long id,
        @Email
        String email,
        Perfil perfil,
        LocalDateTime dataCadastro) {
}
