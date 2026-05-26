package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;

public record PedidoItemResponseDTO(
        Long id,
        Long jogoId,
        String jogoNome,
        int quantidade,
        BigDecimal precoUnitario,
        BigDecimal totalItem) {
}
