package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioDigitalResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.RelogioDigital;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;

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

        return new RelogioDigitalResponseDTO(
            relogio.getId(),
            relogio.getModelo(),
            relogio.getDimensoes(),
            relogio.getIncremenento(),
            relogio.getDisplayDuplo(),
            relogio.getTemBuzzer(),
            relogio.getModoTempo(),
            relogio.getFabricante() != null ? relogio.getFabricante().getId() : null,
            relogio.getDataCadastro()
        );
    }
}
