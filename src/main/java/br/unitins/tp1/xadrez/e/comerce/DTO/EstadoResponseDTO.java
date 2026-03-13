package br.unitins.tp1.xadrez.e.comerce.DTO;

import br.unitins.tp1.xadrez.e.comerce.model.Regiao;

public record EstadoResponseDTO(
    Long id,
    String sigla,
    String nome,
    Regiao regiao){
    
}
