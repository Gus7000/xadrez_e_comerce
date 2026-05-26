package br.unitins.tp1.xadrez.e.comerce.DTO;

import br.unitins.tp1.xadrez.e.comerce.model.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotBlank(message = "O email é obrigatório")
        @Email
        String email,
        @NotBlank(message = "A senha é obrigatória")
        String senha,
        @NotNull(message = "O perfil é obrigatório")
        Perfil perfil) {
}
