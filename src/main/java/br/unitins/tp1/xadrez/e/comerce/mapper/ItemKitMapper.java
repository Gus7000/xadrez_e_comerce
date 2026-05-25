package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.ItemKitResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.ItemKitClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PecaClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PecaResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.ItemKit;

public class ItemKitMapper {
    public static ItemKitResponseDTO toResponseDTO(ItemKit itemKit) {
        if (itemKit == null)
            return null;
        
        PecaResponseDTO pecaDTO = itemKit.getPeca() != null 
            ? PecaMapper.toResponseDTO(itemKit.getPeca())
            : null;
        
        return new ItemKitResponseDTO(
            itemKit.getId(),
            pecaDTO,
            itemKit.getQuantidade()
        );
    }

    public static ItemKitClienteResponseDTO toClienteResponseDTO(ItemKit itemKit) {
        if (itemKit == null)
            return null;

        PecaClienteResponseDTO pecaDTO = itemKit.getPeca() != null
            ? PecaMapper.toClienteResponseDTO(itemKit.getPeca())
            : null;

        return new ItemKitClienteResponseDTO(
            itemKit.getId(),
            pecaDTO,
            itemKit.getQuantidade()
        );
    }
}
