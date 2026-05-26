package br.unitins.tp1.xadrez.e.comerce.DTO;

import br.unitins.tp1.xadrez.e.comerce.model.PedidoStatus;
import jakarta.validation.constraints.NotNull;

public record PedidoStatusUpdateDTO(
        @NotNull(message = "status é obrigatório")
        PedidoStatus status) {
}
