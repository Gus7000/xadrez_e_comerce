package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioPerfilUpdateDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
        String nome,
        @NotBlank(message = "O cpf é obrigatório")
        @Size(max = 20, message = "O cpf deve ter no máximo 20 caracteres")
        String cpf,
        @NotBlank(message = "O telefone é obrigatório")
        @Size(max = 40, message = "O telefone deve ter no máximo 40 caracteres")
        String telefone) {
}
