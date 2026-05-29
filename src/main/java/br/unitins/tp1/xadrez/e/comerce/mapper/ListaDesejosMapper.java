package br.unitins.tp1.xadrez.e.comerce.mapper;

import java.util.Comparator;
import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezSimplesResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.ListaDesejosResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;

public class ListaDesejosMapper {

    public static ListaDesejosResponseDTO toResponseDTO(ListaDesejos listaDesejos) {
        if (listaDesejos == null) {
            return null;
        }

        List<JogoXadrezSimplesResponseDTO> jogos = listaDesejos.getJogos().stream()
            .map(jogo -> new JogoXadrezSimplesResponseDTO(jogo.getId(), jogo.getNome(), jogo.getPreco()))
            .sorted(Comparator.comparing(JogoXadrezSimplesResponseDTO::id))
            .toList();

        return new ListaDesejosResponseDTO(
                listaDesejos.getId(),
                listaDesejos.getUsuario() != null ? listaDesejos.getUsuario().getId() : null,
                listaDesejos.getUsuario() != null ? listaDesejos.getUsuario().getEmail() : null,
            jogos,
                listaDesejos.getDataCadastro());
    }
}
