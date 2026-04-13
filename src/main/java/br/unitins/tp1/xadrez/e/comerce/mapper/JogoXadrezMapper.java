package br.unitins.tp1.xadrez.e.comerce.mapper;

import java.util.stream.Collectors;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;

public class JogoXadrezMapper {
    public static JogoXadrez toEntity(JogoXadrezRequestDTO dto, KitPeca kitPeca, Tabuleiro tabuleiro) {
        if (dto == null)
            return null;
        JogoXadrez jogoXadrez = new JogoXadrez();
        jogoXadrez.setNome(dto.nome());
        jogoXadrez.setPreco(dto.preco());
        jogoXadrez.setDescricao(dto.descricao());
        jogoXadrez.setEstoqueDisponivel(dto.estoqueDisponivel());
        jogoXadrez.setKitPeca(kitPeca);
        jogoXadrez.setTabuleiro(tabuleiro);
        return jogoXadrez;
    }

    public static JogoXadrezResponseDTO toResponseDTO(JogoXadrez jogoXadrez) {
        if (jogoXadrez == null)
            return null;
        if (jogoXadrez.getKitPeca() == null || jogoXadrez.getTabuleiro() == null)
            return null;

        return new JogoXadrezResponseDTO(
            jogoXadrez.getId(),
            jogoXadrez.getNome(),
            jogoXadrez.getPreco(),
            jogoXadrez.getDescricao(),
            jogoXadrez.getEstoqueDisponivel(),
            jogoXadrez.getKitPeca().getId(),
            jogoXadrez.getKitPeca().getItens().stream().map(ItemKitMapper::toResponseDTO).collect(Collectors.toList()),
            jogoXadrez.getTabuleiro().getId(),
            jogoXadrez.getTabuleiro().getTamanho(),
            jogoXadrez.getRelogio() != null ? jogoXadrez.getRelogio().getId() : null,
            jogoXadrez.getDataCadastro()
        );
    }
}
