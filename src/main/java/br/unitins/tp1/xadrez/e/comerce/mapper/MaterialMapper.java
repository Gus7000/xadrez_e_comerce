package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Material;

public class MaterialMapper {
    public static Material toEntity(MaterialRequestDTO dto) {
        if (dto == null)
            return null;
        Material material = new Material();
        material.setTipo(dto.tipo());
        return material;
    }

    public static MaterialResponseDTO toResponseDTO(Material material) {
        if (material == null)
            return null;
        return new MaterialResponseDTO(
            material.getId(),
            material.getTipo()
        );
    }
}
