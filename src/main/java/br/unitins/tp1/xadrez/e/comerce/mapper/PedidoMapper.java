package br.unitins.tp1.xadrez.e.comerce.mapper;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoItemResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.PedidoItem;

public class PedidoMapper {

    public static PedidoResponseDTO toResponseDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        List<PedidoItemResponseDTO> items = pedido.getItems().stream()
                .map(PedidoMapper::toItemResponseDTO)
                .toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getUsuario() != null ? pedido.getUsuario().getId() : null,
                pedido.getUsuario() != null ? pedido.getUsuario().getEmail() : null,
                pedido.getStatus(),
                pedido.getCupom() != null ? pedido.getCupom().getId() : null,
                pedido.getSubtotal(),
                pedido.getDesconto(),
                pedido.getFrete(),
                pedido.getTaxas(),
                pedido.getValorTotal(),
                items,
                pedido.getDataCadastro());
    }

    public static PedidoItemResponseDTO toItemResponseDTO(PedidoItem item) {
        if (item == null) {
            return null;
        }

        BigDecimal totalItem = item.getPrecoUnitario() != null
                ? item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))
                : null;

        return new PedidoItemResponseDTO(
                item.getId(),
                item.getJogo() != null ? item.getJogo().getId() : null,
                item.getJogo() != null ? item.getJogo().getNome() : null,
                item.getQuantidade(),
                item.getPrecoUnitario(),
                totalItem);
    }
}
