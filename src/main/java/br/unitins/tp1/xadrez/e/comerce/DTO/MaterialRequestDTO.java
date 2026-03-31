package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MaterialRequestDTO(
    @NotBlank(message = "tipo não pode estar em branco")
    @Size(max = 100, message = "tipo pode ter no Maximo 100 caracteres")
    String tipo
) {
}
