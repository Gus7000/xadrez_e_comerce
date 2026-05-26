package br.unitins.tp1.xadrez.e.comerce.mapper;

import java.math.BigDecimal;

import br.unitins.tp1.xadrez.e.comerce.DTO.CupomDescontoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.CupomDescontoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDescontoFixo;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDescontoPercentual;

public class CupomDescontoMapper {

    public static CupomDesconto toEntity(CupomDescontoRequestDTO dto) {
        if (dto == null) return null;

        CupomDesconto entidade;
        if ("FIXO".equalsIgnoreCase(dto.tipo())) {
            CupomDescontoFixo fixo = new CupomDescontoFixo();
            fixo.setValorDesconto(dto.valorDesconto());
            entidade = fixo;
        } else {
            CupomDescontoPercentual perc = new CupomDescontoPercentual();
            perc.setPercentualDesconto(dto.percentualDesconto());
            entidade = perc;
        }

        entidade.setCodigo(dto.codigo());
        entidade.setDataValidade(dto.dataValidade());
        entidade.setAtivo(dto.ativo() != null ? dto.ativo() : Boolean.FALSE);
        entidade.setUsoMaximo(dto.usoMaximo());
        entidade.setPorUsuario(dto.porUsuario() != null ? dto.porUsuario() : Boolean.FALSE);
        entidade.setExpiraEm(dto.expiraEm());

        return entidade;
    }

    public static CupomDescontoResponseDTO toResponseDTO(CupomDesconto entidade) {
        if (entidade == null) return null;

        Double percentual = null;
        BigDecimal valor = null;
        String tipo = null;

        if (entidade instanceof CupomDescontoFixo) {
            tipo = "FIXO";
            valor = ((CupomDescontoFixo) entidade).getValorDesconto();
        } else if (entidade instanceof CupomDescontoPercentual) {
            tipo = "PERCENTUAL";
            percentual = ((CupomDescontoPercentual) entidade).getPercentualDesconto();
        }

        return new CupomDescontoResponseDTO(
            entidade.getId(),
            entidade.getCodigo(),
            tipo,
            entidade.getDataValidade(),
            entidade.isAtivo(),
            entidade.getUsoMaximo(),
            entidade.getUsosRealizados(),
            entidade.isPorUsuario(),
            entidade.getExpiraEm(),
            percentual,
            valor,
            entidade.getDataCadastro()
        );
    }
}
