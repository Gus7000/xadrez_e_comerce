package br.unitins.tp1.xadrez.e.comerce.DTO;

import br.unitins.tp1.xadrez.e.comerce.model.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotBlank(message = "O login é obrigatório")
        String login,
        @NotBlank(message = "A senha é obrigatória")
        String senha,
        @NotNull(message = "O perfil é obrigatório")
        Perfil perfil) {
}
