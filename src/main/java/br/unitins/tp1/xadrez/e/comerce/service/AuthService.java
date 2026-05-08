package br.unitins.tp1.xadrez.e.comerce.service;

import br.unitins.tp1.xadrez.e.comerce.DTO.AuthRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO dto);
}