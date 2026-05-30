package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.PagamentoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoBoleto;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoCartaoCredito;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoCartaoDebito;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoPix;

public class PagamentoMapper {

    public static PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
        if (pagamento == null) {
            return null;
        }

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getPedido() != null ? pagamento.getPedido().getId() : null,
                resolveTipo(pagamento),
                pagamento.getStatus(),
                pagamento.getIdentificadorTransacao(),
                pagamento.getValor(),
                pagamento.getDataConfirmacao(),
                pagamento instanceof PagamentoPix pix ? pix.getChavePix() : null,
                pagamento instanceof PagamentoCartaoCredito credito ? credito.getBandeira() : null,
                pagamento instanceof PagamentoCartaoCredito credito ? credito.getParcelas() : null,
                pagamento instanceof PagamentoCartaoCredito credito ? credito.getNomeTitular() : null,
                pagamento instanceof PagamentoCartaoCredito credito ? credito.getUltimosDigitos() : null,
                pagamento instanceof PagamentoCartaoDebito debito ? debito.getBanco() : null,
                pagamento instanceof PagamentoBoleto boleto ? boleto.getLinhaDigitavel() : null,
                pagamento instanceof PagamentoBoleto boleto ? boleto.getDataVencimento() : null,
                pagamento.getDataCadastro());
    }

    private static String resolveTipo(Pagamento pagamento) {
        if (pagamento instanceof PagamentoPix) {
            return "PIX";
        }
        if (pagamento instanceof PagamentoCartaoCredito) {
            return "CARTAO_CREDITO";
        }
        if (pagamento instanceof PagamentoCartaoDebito) {
            return "CARTAO_DEBITO";
        }
        if (pagamento instanceof PagamentoBoleto) {
            return "BOLETO";
        }
        return null;
    }
}
