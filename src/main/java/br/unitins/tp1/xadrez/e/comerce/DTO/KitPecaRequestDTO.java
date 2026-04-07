package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record KitPecaRequestDTO(
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
    @NotNull(message = "A lista de itens não pode ser nula")
    List<ItemKitRequestDTO> itens
) {
}
