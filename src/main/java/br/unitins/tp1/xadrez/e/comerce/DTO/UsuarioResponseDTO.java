package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

import br.unitins.tp1.xadrez.e.comerce.model.Perfil;

public record UsuarioResponseDTO(
        Long id,
        String login,
        Perfil perfil,
        LocalDateTime dataCadastro) {
}
