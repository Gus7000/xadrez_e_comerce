package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroCompletoDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
        String nome,
        @NotBlank(message = "O cpf é obrigatório")
        @Size(max = 20, message = "O cpf deve ter no máximo 20 caracteres")
        String cpf,
        @NotBlank(message = "O telefone é obrigatório")
        @Size(max = 40, message = "O telefone deve ter no máximo 40 caracteres")
        String telefone,
        @NotBlank(message = "A rua é obrigatória")
        @Size(max = 150, message = "A rua deve ter no máximo 150 caracteres")
        String rua,
        @NotBlank(message = "O número é obrigatório")
        @Size(max = 20, message = "O número deve ter no máximo 20 caracteres")
        String numero,
        @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres")
        String complemento,
        @NotBlank(message = "O cep é obrigatório")
        @Size(max = 20, message = "O cep deve ter no máximo 20 caracteres")
        String cep,
        @NotBlank(message = "A cidade é obrigatória")
        @Size(max = 80, message = "A cidade deve ter no máximo 80 caracteres")
        String cidade,
        @NotBlank(message = "O estado é obrigatório")
        @Size(max = 80, message = "O estado deve ter no máximo 80 caracteres")
        String estado,
        @NotBlank(message = "O país é obrigatório")
        @Size(max = 80, message = "O país deve ter no máximo 80 caracteres")
        String pais) {
}