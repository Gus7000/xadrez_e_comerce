package br.unitins.tp1.xadrez.e.comerce.mapper;

import java.util.stream.Collectors;

import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.model.ItemKit;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.repository.PecaRepository;

public class KitPecaMapper {
    public static KitPeca toEntity(KitPecaRequestDTO dto, PecaRepository pecaRepository, Fabricante fabricante) {
        if (dto == null)
            return null;
        KitPeca kitPeca = new KitPeca();
        kitPeca.setNome(dto.nome());
        kitPeca.setPreco(dto.preco());
        kitPeca.setDescricao(dto.descricao());
        kitPeca.setFabricante(fabricante);
        kitPeca.setItens(dto.itens().stream()
            .map(itemDto -> {
                ItemKit item = new ItemKit();
                item.setKit(kitPeca);
                item.setPeca(pecaRepository.findById(itemDto.pecaId()));
                item.setQuantidade(itemDto.quantidade());
                return item;
            })
            .collect(Collectors.toList()));
        return kitPeca;
    }

    public static KitPecaResponseDTO toResponseDTO(KitPeca kitPeca) {
        if (kitPeca == null)
            return null;
        return new KitPecaResponseDTO(
            kitPeca.getId(),
            kitPeca.getNome(),
            kitPeca.getPreco(),
            kitPeca.getDescricao(),
            kitPeca.getFabricante() != null ? kitPeca.getFabricante().getId() : null,
            kitPeca.getFabricante() != null ? kitPeca.getFabricante().getNome() : null,
            kitPeca.getItens().stream().map(ItemKitMapper::toResponseDTO).collect(Collectors.toList()),
            kitPeca.getDataCadastro()
        );
    }
}
