package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;

public class TabuleiroMapper {
    public static Tabuleiro toEntity(TabuleiroRequestDTO dto, Material material, Fabricante fabricante) {
        if (dto == null)
            return null;
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setNome(dto.nome());
        tabuleiro.setPreco(dto.preco());
        tabuleiro.setDescricao(dto.descricao());
        tabuleiro.setFabricante(fabricante);
        tabuleiro.setTamanho(dto.tamanho());
        tabuleiro.setCor(CorPeca.valueOf(dto.corId()));
        tabuleiro.setMaterial(material);
        return tabuleiro;
    }

    public static TabuleiroResponseDTO toResponseDTO(Tabuleiro tabuleiro) {
        if (tabuleiro == null)
            return null;
        return new TabuleiroResponseDTO(
            tabuleiro.getId(),
            tabuleiro.getNome(),
            tabuleiro.getPreco(),
            tabuleiro.getDescricao(),
            tabuleiro.getFabricante() != null ? tabuleiro.getFabricante().getId() : null,
            tabuleiro.getFabricante() != null ? tabuleiro.getFabricante().getNome() : null,
            tabuleiro.getTamanho(),
            tabuleiro.getCor().getNome(),
            tabuleiro.getCor().getId(),
            tabuleiro.getMaterial().getId(),
            tabuleiro.getMaterial().getTipo(),
            tabuleiro.getDataCadastro()
        );
    }
}
