package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.CupomDescontoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.CupomDescontoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;

public class CupomDescontoMapper {

    public static CupomDesconto toEntity(CupomDescontoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        CupomDesconto entidade = new CupomDesconto();

        entidade.setCodigo(dto.codigo());
        entidade.setTipo(dto.tipo());
        entidade.setDataValidade(dto.dataValidade());
        entidade.setAtivo(dto.ativo() != null ? dto.ativo() : Boolean.FALSE);
        entidade.setUsoMaximo(dto.usoMaximo());
        entidade.setPorUsuario(dto.porUsuario() != null ? dto.porUsuario() : Boolean.FALSE);
        entidade.setValor(dto.valor());

        return entidade;
    }

    public static CupomDescontoResponseDTO toResponseDTO(CupomDesconto entidade) {
        if (entidade == null) {
            return null;
        }
        return entidade.toDTO();
    }
}
