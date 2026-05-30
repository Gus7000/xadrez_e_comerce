package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;
import java.util.List;




public record MeResponseDTO(
        Long id,
        String email,
        String keycloakId,
        String nome,
        String telefone,
        String cpf,
        boolean cadastroCompleto,
        List<EnderecoResponseDTO> enderecos,
        LocalDateTime dataCadastro) {
}