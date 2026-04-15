package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioDigitalResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.RelogioDigital;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;
import java.util.stream.Collectors;

public class RelogioDigitalMapper {
    public static RelogioDigital toEntity(RelogioRequestDTO dto, FabricanteRepository fabricanteRepository) {
        if (dto == null)
            return null;
        RelogioDigital relogio = new RelogioDigital();
        relogio.setModelo(dto.modelo());
        relogio.setDimensoes(dto.dimensoes());
        relogio.setFabricante(fabricanteRepository.findById(dto.fabricanteId()));
        return relogio;
    }

    public static RelogioDigitalResponseDTO toResponseDTO(RelogioDigital relogio) {
        if (relogio == null)
            return null;

        FabricanteResponseDTO fabricanteDTO = relogio.getFabricante() != null 
            ? FabricanteMapper.toResponseDTO(relogio.getFabricante())
            : null;

        return new RelogioDigitalResponseDTO(
            relogio.getId(),
            relogio.getModelo(),
            relogio.getDimensoes(),
            relogio.getIncremenento(),
            relogio.getDisplayDuplo(),
            relogio.getTemBuzzer(),
            relogio.getModoTempo() != null 
                ? relogio.getModoTempo().stream().map(m -> m.getNome()).collect(Collectors.toSet())
                : null,
            fabricanteDTO,
            relogio.getDataCadastro()
        );
    }
}
