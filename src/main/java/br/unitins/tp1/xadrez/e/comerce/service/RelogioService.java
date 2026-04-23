package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;

public interface RelogioService {
    List<Relogio> findAll();

    Relogio findById(Long id);

    List<Relogio> findByMarca(String marca);

    List <Relogio> findByTipo(Long idTipo);

    Relogio create(RelogioRequestDTO dto);

    void update(Long id, RelogioRequestDTO dto);

    void delete(Long id);
}
