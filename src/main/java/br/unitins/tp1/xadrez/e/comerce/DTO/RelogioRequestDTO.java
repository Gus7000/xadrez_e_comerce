package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RelogioRequestDTO(
    @NotBlank(message = "O modelo não pode estar em branco")
    String modelo,
    @NotNull(message = "O tipo do relógio é obrigatório")
    @Min(value = 1, message = "O id precisa ser 1 ou 2")
    @Max(value = 2, message = "O id precisa ser 1 ou 2")
    Long idTipoRelogio
) {
}
