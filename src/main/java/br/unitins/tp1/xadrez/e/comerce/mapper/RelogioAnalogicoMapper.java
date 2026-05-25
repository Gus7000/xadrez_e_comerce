package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioAnalogicoClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioAnalogicoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.RelogioAnalogico;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;

public class RelogioAnalogicoMapper {
    public static RelogioAnalogico toEntity(RelogioRequestDTO dto, FabricanteRepository fabricanteRepository) {
        if (dto == null)
            return null;
        RelogioAnalogico relogio = new RelogioAnalogico();
        relogio.setModelo(dto.modelo());
        relogio.setDimensoes(dto.dimensoes());
        relogio.setFabricante(fabricanteRepository.findById(dto.fabricanteId()));
        return relogio;
    }

    public static RelogioAnalogicoResponseDTO toResponseDTO(RelogioAnalogico relogio) {
        if (relogio == null)
            return null;

        FabricanteResponseDTO fabricanteDTO = relogio.getFabricante() != null 
            ? FabricanteMapper.toResponseDTO(relogio.getFabricante())
            : null;

        return new RelogioAnalogicoResponseDTO(
            relogio.getId(),
            relogio.getModelo(),
            relogio.getDimensoes(),
            relogio.getTemBandeira(),
            relogio.getNecessitaPilha(),
            relogio.getMecanismo(),
            fabricanteDTO,
            relogio.getDataCadastro()
        );
    }

    public static RelogioAnalogicoClienteResponseDTO toClienteResponseDTO(RelogioAnalogico relogio) {
        if (relogio == null)
            return null;

        FabricanteClienteResponseDTO fabricanteDTO = relogio.getFabricante() != null
            ? FabricanteMapper.toClienteResponseDTO(relogio.getFabricante())
            : null;

        return new RelogioAnalogicoClienteResponseDTO(
            relogio.getId(),
            relogio.getModelo(),
            relogio.getDimensoes(),
            relogio.getTemBandeira(),
            relogio.getNecessitaPilha(),
            relogio.getMecanismo(),
            fabricanteDTO
        );
    }
}
