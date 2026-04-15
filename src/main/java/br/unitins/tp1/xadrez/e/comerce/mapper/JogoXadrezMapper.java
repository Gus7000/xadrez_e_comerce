package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroResponseDTO;
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

        KitPecaResponseDTO kitPecaDTO = KitPecaMapper.toResponseDTO(jogoXadrez.getKitPeca());
        TabuleiroResponseDTO tabuleiroDTO = TabuleiroMapper.toResponseDTO(jogoXadrez.getTabuleiro());
        RelogioResponseDTO relogioDTO = jogoXadrez.getRelogio() != null ? RelogioMapper.toResponseDTO(jogoXadrez.getRelogio()) : null;

        return new JogoXadrezResponseDTO(
            jogoXadrez.getId(),
            jogoXadrez.getNome(),
            jogoXadrez.getPreco(),
            jogoXadrez.getDescricao(),
            jogoXadrez.getEstoqueDisponivel(),
            kitPecaDTO,
            tabuleiroDTO,
            relogioDTO,
            jogoXadrez.getDataCadastro()
        );
    }
}
