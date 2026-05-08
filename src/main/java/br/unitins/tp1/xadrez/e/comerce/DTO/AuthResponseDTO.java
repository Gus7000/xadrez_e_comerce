package br.unitins.tp1.xadrez.e.comerce.DTO;

import br.unitins.tp1.xadrez.e.comerce.model.Perfil;

public record AuthResponseDTO(
        String login,
        String token,
        Perfil perfil) {
}