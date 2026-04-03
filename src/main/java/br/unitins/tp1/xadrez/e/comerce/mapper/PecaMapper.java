package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.PecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PecaResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;

public class PecaMapper {
    public static Peca toEntity(PecaRequestDTO dto, Material material) {
        if (dto == null)
            return null;
        Peca peca = new Peca();
        peca.setCor(CorPeca.valueOf(dto.corId()));
        peca.setTipo(TipoPeca.valueOf(dto.tipoId()));
        peca.setMaterial(material);
        return peca;
    }

    public static PecaResponseDTO toResponseDTO(Peca peca) {
        if (peca == null)
            return null;
        return new PecaResponseDTO(
            peca.getId(),
            peca.getCor().getNome(),
            peca.getCor().getId(),
            peca.getTipo().getNome(),
            peca.getTipo().getId(),
            peca.getMaterial().getId(),
            peca.getMaterial().getTipo(),
            peca.getDataCadastro()
        );
    }
}
