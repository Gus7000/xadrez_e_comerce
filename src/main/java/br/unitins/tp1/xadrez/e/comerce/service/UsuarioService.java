package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.MeResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioPerfilUpdateDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import org.keycloak.representations.idm.UserRepresentation;

public interface UsuarioService {

    List<Usuario> findAll();

    Usuario findById(Long id);

    Usuario findByEmail(String email);

    Usuario findByKeycloakId(String keycloakId);

    Usuario localizarOuCriarPorKeycloak(UserRepresentation keycloakUser);

    Usuario obterOuCriarUsuarioLocal();

    MeResponseDTO obterMeuPerfil();

    Usuario atualizarPerfil(UsuarioPerfilUpdateDTO dto);

    void delete(Long id);

    void delete(String identificador);

    void promoteToAdmin(Long id);

    void promoteToAdmin(String identificador);

    void demoteFromAdmin(Long id);

    void demoteFromAdmin(String identificador);
}
