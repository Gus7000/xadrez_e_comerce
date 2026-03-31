package br.unitins.tp1.xadrez.e.comerce.DTO;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FabricanteRequestDTO(
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve conter entra 3 a 100 caracteres")
    String nome,
    @NotBlank(message = "O CNPJ não pode estar em branco")
    @CNPJ(message = "CNPJ inválido")
    String cnpj,
    @NotBlank(message = "O telefone não pode estar em branco")
    // Valida formato: (XX) 9XXXX-XXXX ou (XX) XXXX-XXXX
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$", 
             message = "Formato de telefone inválido. Use: (99) 99999-9999")
    String telefone
) {
}
