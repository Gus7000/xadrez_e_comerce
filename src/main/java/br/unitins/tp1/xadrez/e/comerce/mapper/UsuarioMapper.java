package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;

public class UsuarioMapper {

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getKeycloakId(),
                usuario.getDataCadastro());
    }
}
