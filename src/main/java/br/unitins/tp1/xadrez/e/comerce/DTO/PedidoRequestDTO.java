package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.PedidoStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
        @NotNull(message = "usuarioId é obrigatório")
        Long usuarioId,
        @NotEmpty(message = "items não pode ser vazio")
        List<@Valid PedidoItemRequestDTO> items,
        Long cupomId,
        PedidoStatus status) {
}
