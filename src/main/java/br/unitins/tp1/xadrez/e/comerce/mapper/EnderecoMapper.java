package br.unitins.tp1.xadrez.e.comerce.mapper;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;

public class EnderecoMapper {

    public static Endereco toEntity(EnderecoRequestDTO dto, Usuario usuario) {
        if (dto == null) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setCep(dto.cep());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setPais(dto.pais());
        endereco.setUsuario(usuario);

        return endereco;
    }

    public static EnderecoResponseDTO toResponseDTO(Endereco e) {
        return new EnderecoResponseDTO(
                e.getId(),
                e.getRua(),
                e.getNumero(),
                e.getComplemento(),
                e.getCep(),
                e.getCidade(),
                e.getEstado(),
                e.getPais(),
                e.getDataCadastro());
    }

    public static EnderecoResponseDTO toResponse(Endereco e) {
        return toResponseDTO(e);
    }
}
