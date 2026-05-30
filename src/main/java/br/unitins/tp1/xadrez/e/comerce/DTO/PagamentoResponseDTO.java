package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.unitins.tp1.xadrez.e.comerce.model.PagamentoStatus;

public record PagamentoResponseDTO(
        Long id,
        Long pedidoId,
        String tipo,
        PagamentoStatus status,
        String identificadorTransacao,
        BigDecimal valor,
        LocalDateTime dataConfirmacao,
        String chavePix,
        String bandeira,
        Integer parcelas,
        String nomeTitular,
        String ultimosDigitos,
        String banco,
        String linhaDigitavel,
        LocalDate dataVencimento,
        LocalDateTime dataCadastro) {
}
