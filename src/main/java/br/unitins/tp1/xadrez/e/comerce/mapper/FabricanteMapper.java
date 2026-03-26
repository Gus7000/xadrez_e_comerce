package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;

public class FabricanteMapper {
    public static Fabricante toEntity(FabricanteRequestDTO dto) {
        if (dto == null)
            return null;
        Fabricante fabricante = new Fabricante();
        fabricante.setNome(dto.nome());
        fabricante.setCnpj(dto.cnpj());
        fabricante.setTelefone(dto.telefone());
        return fabricante;
    }

    public static FabricanteResponseDTO toResponseDTO(Fabricante fabricante) {
        if (fabricante == null)
            return null;
        return new FabricanteResponseDTO(
            fabricante.getId(),
            fabricante.getNome(),
            fabricante.getCnpj(),
            fabricante.getTelefone()
        );
    }
}
