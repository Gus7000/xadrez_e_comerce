package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ListaDesejosRequestDTO(
        @NotNull(message = "usuarioId é obrigatório")
        Long usuarioId,
        @NotEmpty(message = "jogoIds não pode ser vazio")
        List<@NotNull(message = "jogoId não pode ser nulo") Long> jogoIds) {
}
