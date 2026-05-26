package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.PagamentoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;

public class PagamentoMapper {

    public static PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
        if (pagamento == null) {
            return null;
        }

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getPedido() != null ? pagamento.getPedido().getId() : null,
                pagamento.getMetodo(),
                pagamento.getStatus(),
                pagamento.getIdentificadorTransacao(),
                pagamento.getValor(),
                pagamento.getDataCadastro());
    }
}
