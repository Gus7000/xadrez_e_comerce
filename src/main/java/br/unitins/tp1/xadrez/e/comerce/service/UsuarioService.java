package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.MeResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioPerfilUpdateDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;

public interface UsuarioService {

    List<Usuario> findAll();

    Usuario findById(Long id);

    Usuario findByEmail(String email);

    Usuario findByKeycloakId(String keycloakId);

    Usuario obterOuCriarUsuarioLocal();

    MeResponseDTO obterMeuPerfil();

    Usuario atualizarPerfil(UsuarioPerfilUpdateDTO dto);

    Usuario create(UsuarioRequestDTO dto);

    void update(Long id, UsuarioRequestDTO dto);

    void delete(Long id);
}
