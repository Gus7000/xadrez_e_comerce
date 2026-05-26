package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.PedidoStatus;

public record PedidoResponseDTO(
        Long id,
        Long usuarioId,
        String usuarioEmail,
        PedidoStatus status,
        Long cupomId,
        BigDecimal subtotal,
        BigDecimal desconto,
        BigDecimal frete,
        BigDecimal taxas,
        BigDecimal valorTotal,
        List<PedidoItemResponseDTO> items,
        LocalDateTime dataCadastro) {
}
