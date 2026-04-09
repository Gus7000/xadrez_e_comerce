package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;

public class TabuleiroMapper {
    public static Tabuleiro toEntity(TabuleiroRequestDTO dto, Material material, FabricanteRepository fabricanteRepository) {
        if (dto == null)
            return null;
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setTamanho(dto.tamanho());
        tabuleiro.setMaterial(material);
        tabuleiro.setFabricante(fabricanteRepository.findById(dto.fabricanteId()));
        return tabuleiro;
    }

    public static TabuleiroResponseDTO toResponseDTO(Tabuleiro tabuleiro) {
        if (tabuleiro == null)
            return null;
        return new TabuleiroResponseDTO(
            tabuleiro.getId(),
            tabuleiro.getTamanho(),
            tabuleiro.getMaterial().getId(),
            tabuleiro.getMaterial().getTipo(),
            tabuleiro.getFabricante() != null ? tabuleiro.getFabricante().getId() : null,
            tabuleiro.getDataCadastro()
        );
    }
}
