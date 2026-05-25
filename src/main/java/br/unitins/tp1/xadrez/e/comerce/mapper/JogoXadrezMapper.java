package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;

public class JogoXadrezMapper {
    public static JogoXadrez toEntity(JogoXadrezRequestDTO dto, KitPeca kitPeca, Tabuleiro tabuleiro, Relogio relogio) {
        if (dto == null)
            return null;
        JogoXadrez jogoXadrez = new JogoXadrez();
        jogoXadrez.setNome(dto.nome());
        jogoXadrez.setPreco(dto.preco());
        jogoXadrez.setDescricao(dto.descricao());
        jogoXadrez.setEstoqueDisponivel(dto.estoqueDisponivel());
        jogoXadrez.setKitPeca(kitPeca);
        jogoXadrez.setTabuleiro(tabuleiro);
        jogoXadrez.setRelogio(relogio);
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

    public static JogoXadrezClienteResponseDTO toClienteResponseDTO(JogoXadrez jogoXadrez) {
        if (jogoXadrez == null)
            return null;
        if (jogoXadrez.getKitPeca() == null || jogoXadrez.getTabuleiro() == null)
            return null;

        KitPecaClienteResponseDTO kitPecaDTO = KitPecaMapper.toClienteResponseDTO(jogoXadrez.getKitPeca());
        TabuleiroClienteResponseDTO tabuleiroDTO = TabuleiroMapper.toClienteResponseDTO(jogoXadrez.getTabuleiro());
        RelogioClienteResponseDTO relogioDTO = jogoXadrez.getRelogio() != null ? RelogioMapper.toClienteResponseDTO(jogoXadrez.getRelogio()) : null;

        return new JogoXadrezClienteResponseDTO(
            jogoXadrez.getId(),
            jogoXadrez.getNome(),
            jogoXadrez.getPreco(),
            jogoXadrez.getDescricao(),
            kitPecaDTO,
            tabuleiroDTO,
            relogioDTO
        );
    }
}
