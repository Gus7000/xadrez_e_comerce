package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

public record EnderecoResponseDTO(
        Long id,
        String rua,
        String numero,
        String complemento,
        String cep,
        String cidade,
        String estado,
        String pais,
        LocalDateTime dataCadastro) {
}
