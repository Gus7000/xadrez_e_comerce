package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioAnalogicoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioDigitalResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.model.RelogioAnalogico;
import br.unitins.tp1.xadrez.e.comerce.model.RelogioDigital;

public class RelogioMapper {
    
    public static RelogioResponseDTO toResponseDTO(Relogio relogio) {
        if (relogio == null)
            return null;

        Boolean isDigital = relogio instanceof RelogioDigital;
        
        RelogioDigitalResponseDTO relogioDigitalDTO = isDigital 
            ? RelogioDigitalMapper.toResponseDTO((RelogioDigital) relogio) 
            : null;
        
        RelogioAnalogicoResponseDTO relogioAnalogicoDTO = !isDigital 
            ? RelogioAnalogicoMapper.toResponseDTO((RelogioAnalogico) relogio) 
            : null;

        FabricanteResponseDTO fabricanteDTO = relogio.getFabricante() != null 
            ? FabricanteMapper.toResponseDTO(relogio.getFabricante())
            : null;

        return new RelogioResponseDTO(
            relogio.getId(),
            relogio.getModelo(),
            relogio.getDimensoes(),
            fabricanteDTO,
            relogioDigitalDTO,
            relogioAnalogicoDTO,
            relogio.getDataCadastro()
        );
    }
}
