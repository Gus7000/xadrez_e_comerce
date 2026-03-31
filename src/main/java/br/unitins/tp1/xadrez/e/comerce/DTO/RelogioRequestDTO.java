package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RelogioRequestDTO(
    @NotBlank(message = " não pode estar em branco")
    @Size(max = 100)
    String modelo,
    @NotNull(message = "O tipo do relógio é obrigatório")
    @Positive(message = "O id do tipo deve ser positivo")
    Long idTipoRelogio) {
    
}
