package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.model.TipoRelogio;

public class RelogioMapper {
    public static Relogio toEntity(RelogioRequestDTO dto, Fabricante fabricante){
        if (dto == null)
            return null;
        Relogio relogio = new Relogio();
        relogio.setNome(dto.nome());
        relogio.setPreco(dto.preco());
        relogio.setDescricao(dto.descricao());
        relogio.setFabricante(fabricante);
        relogio.setModelo(dto.modelo());
        relogio.setTipo(TipoRelogio.valueOf(dto.idTipoRelogio()));

        return relogio;
    }
    public static RelogioResponseDTO toResponseDTO(Relogio relogio){
        if (relogio == null)
            return null;

        return new RelogioResponseDTO(
            relogio.getId(),
            relogio.getNome(),
            relogio.getPreco(),
            relogio.getDescricao(),
            relogio.getFabricante() != null ? relogio.getFabricante().getId() : null,
            relogio.getFabricante() != null ? relogio.getFabricante().getNome() : null,
            relogio.getModelo(),
            relogio.getTipo(),
            relogio.getDataCadastro()
        );
    }
}
