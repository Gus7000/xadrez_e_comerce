package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;

public interface UsuarioService {

    List<Usuario> findAll();

    Usuario findById(Long id);

    Usuario create(UsuarioRequestDTO dto);

    void update(Long id, UsuarioRequestDTO dto);

    void delete(Long id);
}
