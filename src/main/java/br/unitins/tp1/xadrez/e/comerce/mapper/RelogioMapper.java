package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.model.TipoRelogio;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;

public class RelogioMapper {
    public static Relogio toEntity(RelogioRequestDTO dto, FabricanteRepository fabricanteRepository){
        if (dto == null)
            return null;
        Relogio relogio = new Relogio();
        relogio.setModelo(dto.modelo());
        relogio.setTipo(TipoRelogio.valueOf(dto.idTipoRelogio()));
        relogio.setFabricante(fabricanteRepository.findById(dto.fabricanteId()));

        return relogio;
    }
    public static RelogioResponseDTO toResponseDTO(Relogio relogio){
        if (relogio == null)
            return null;

        return new RelogioResponseDTO(
            relogio.getId(),
            relogio.getModelo(),
            relogio.getTipo(),
            relogio.getFabricante() != null ? relogio.getFabricante().getId() : null,
            relogio.getDataCadastro()
        );
    }
}
