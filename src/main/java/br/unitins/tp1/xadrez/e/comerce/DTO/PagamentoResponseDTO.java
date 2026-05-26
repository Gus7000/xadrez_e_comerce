package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.unitins.tp1.xadrez.e.comerce.model.MetodoPagamento;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoStatus;

public record PagamentoResponseDTO(
        Long id,
        Long pedidoId,
        MetodoPagamento metodo,
        PagamentoStatus status,
        String identificadorTransacao,
        BigDecimal valor,
        LocalDateTime dataCadastro) {
}
