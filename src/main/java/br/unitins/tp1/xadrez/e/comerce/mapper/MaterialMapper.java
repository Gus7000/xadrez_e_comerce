package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialAdminResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Material;

public class MaterialMapper {
    public static Material toEntity(MaterialRequestDTO dto) {
        if (dto == null)
            return null;
        Material material = new Material();
        material.setNome(dto.nome());
        return material;
    }

    public static MaterialResponseDTO toResponseDTO(Material material) {
        if (material == null)
            return null;
        return new MaterialResponseDTO(
            material.getId(),
            material.getNome()
        );
    }

    public static MaterialAdminResponseDTO toAdminResponseDTO(Material material) {
        if (material == null)
            return null;
        return new MaterialAdminResponseDTO(
            material.getId(),
            material.getNome(),
            material.getDataCadastro()
        );
    }
}
