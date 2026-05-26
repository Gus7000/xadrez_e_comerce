package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record MePedidoRequestDTO(
        @NotEmpty(message = "items não pode ser vazio")
        List<@Valid PedidoItemRequestDTO> items,
        Long cupomId,
        br.unitins.tp1.xadrez.e.comerce.model.PedidoStatus status) {
}
