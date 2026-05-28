package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

public record MaterialAdminResponseDTO(
    Long id,
    String nome,
    LocalDateTime dataCadastro
) {
}