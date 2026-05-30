package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.unitins.tp1.xadrez.e.comerce.model.PagamentoStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PagamentoRequestDTO(
        @NotNull(message = "pedidoId é obrigatório")
        Long pedidoId,
        @NotNull(message = "tipo é obrigatório")
        String tipo,
        @NotNull(message = "status é obrigatório")
        PagamentoStatus status,
        @Size(max = 200, message = "identificadorTransacao deve ter no máximo 200 caracteres")
        String identificadorTransacao,
        @NotNull(message = "valor é obrigatório")
        @Positive(message = "valor deve ser positivo")
        BigDecimal valor,
        LocalDateTime dataConfirmacao,
        String chavePix,
        String bandeira,
        Integer parcelas,
        String nomeTitular,
        String ultimosDigitos,
        String banco,
        String linhaDigitavel,
        LocalDate dataVencimento) {
}
