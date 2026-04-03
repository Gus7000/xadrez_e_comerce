package br.unitins.tp1.xadrez.e.comerce.mapper;

import java.util.stream.Collectors;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoCompletoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.JogoCompletoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.JogoCompleto;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;

public class JogoCompletoMapper {
    public static JogoCompleto toEntity(JogoCompletoRequestDTO dto, KitPeca kitPeca, Tabuleiro tabuleiro) {
        if (dto == null)
            return null;
        JogoCompleto jogoCompleto = new JogoCompleto();
        jogoCompleto.setKitPeca(kitPeca);
        jogoCompleto.setTabuleiro(tabuleiro);
        return jogoCompleto;
    }

    public static JogoCompletoResponseDTO toResponseDTO(JogoCompleto jogoCompleto) {
        if (jogoCompleto == null)
            return null;
        
        if (jogoCompleto.getKitPeca() == null || jogoCompleto.getTabuleiro() == null)
            return null;
        
        return new JogoCompletoResponseDTO(
            jogoCompleto.getId(),
            jogoCompleto.getKitPeca().getId(),
            jogoCompleto.getKitPeca().getItens().stream().map(ItemKitMapper::toResponseDTO).collect(Collectors.toList()),
            jogoCompleto.getTabuleiro().getId(),
            jogoCompleto.getTabuleiro().getTamanho(),
            jogoCompleto.getTabuleiro().getCor().getNome(),
            jogoCompleto.getDataCadastro()
        );
    }
}
