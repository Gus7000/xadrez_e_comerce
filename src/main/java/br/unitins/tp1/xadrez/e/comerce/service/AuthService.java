package br.unitins.tp1.xadrez.e.comerce.service;

import br.unitins.tp1.xadrez.e.comerce.DTO.AuthRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.AuthResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioRegisterDTO;

public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO dto);

    AuthResponseDTO register(UsuarioRegisterDTO dto);
}