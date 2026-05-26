package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PedidoItemRequestDTO(
        @NotNull(message = "jogoId é obrigatório")
        Long jogoId,
        @Min(value = 1, message = "quantidade deve ser maior que zero")
        int quantidade,
        @Positive(message = "precoUnitario deve ser positivo")
        BigDecimal precoUnitario) {
}
