package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(
        @NotBlank(message = "rua não pode estar em branco")
        @Size(max = 150, message = "rua deve ter no máximo 150 caracteres")
        String rua,
        @NotBlank(message = "numero não pode estar em branco")
        @Size(max = 20, message = "numero deve ter no máximo 20 caracteres")
        String numero,
        @Size(max = 100, message = "complemento deve ter no máximo 100 caracteres")
        String complemento,
        @NotBlank(message = "cep não pode estar em branco")
        @Size(max = 20, message = "cep deve ter no máximo 20 caracteres")
        String cep,
        @NotBlank(message = "cidade não pode estar em branco")
        @Size(max = 80, message = "cidade deve ter no máximo 80 caracteres")
        String cidade,
        @NotBlank(message = "estado não pode estar em branco")
        @Size(max = 80, message = "estado deve ter no máximo 80 caracteres")
        String estado,
        @NotBlank(message = "pais não pode estar em branco")
        @Size(max = 80, message = "pais deve ter no máximo 80 caracteres")
        String pais,
        @NotNull(message = "usuarioId é obrigatório")
        Long usuarioId) {
}
