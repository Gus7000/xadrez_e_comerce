package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TabuleiroRequestDTO(
    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    String nome,
    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    Double preco,
    @NotBlank(message = "A descrição não pode estar em branco")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    String descricao,
    @NotNull(message = "O fabricante é obrigatório")
    @Positive(message = "O id do fabricante deve ser positivo")
    Long fabricanteId,
    @NotBlank(message = "O tamanho não pode estar em branco")
    @Size(min = 2, max = 50, message = "O tamanho deve conter entre 2 a 50 caracteres")
    String tamanho,
    @NotNull(message = "A cor não pode ser nula")
    @Positive(message = "O id da cor deve ser positivo")
    Long corId,
    @NotNull(message = "O material é obrigatório")
    @Positive(message = "O id do material deve ser positivo")
    Long materialId
) {
}
