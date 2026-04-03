package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.ItemKitResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.ItemKit;

public class ItemKitMapper {
    public static ItemKitResponseDTO toResponseDTO(ItemKit itemKit) {
        if (itemKit == null)
            return null;
        return new ItemKitResponseDTO(
            itemKit.getId(),
            itemKit.getPeca().getId(),
            itemKit.getPeca().getTipo().getNome(),
            itemKit.getPeca().getMaterial().getTipo(),
            itemKit.getPeca().getCor().getNome(),
            itemKit.getQuantidade()
        );
    }
}
