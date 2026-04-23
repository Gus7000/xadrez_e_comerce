package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.ItemKit;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.PecaRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.KitPecaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class KitPecaServiceImpl implements KitPecaService {

    @Inject
    KitPecaRepository repository;

    @Inject
    PecaRepository pecaRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Override
    public List<KitPeca> findAll() {
        return repository.listAll();
    }

    @Override
    public KitPeca findById(Long id) {
        KitPeca kitPeca = repository.findById(id);

        if (kitPeca == null) {
            throw new NotFoundException("Kit de Peças não encontrado");
        }

        return kitPeca;
    }

    @Override
    @Transactional
    public KitPeca create(KitPecaRequestDTO dto) {
        var fabricante = fabricanteRepository.findById(dto.fabricanteId());
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        KitPeca kitPeca = new KitPeca();
        kitPeca.setFabricante(fabricante);
        kitPeca.setItens(dto.itens().stream().map(itemDto -> {
            var peca = pecaRepository.findById(itemDto.pecaId());
            if (peca == null) {
                throw new NotFoundException("Peça com ID " + itemDto.pecaId() + " não encontrada");
            }

            ItemKit item = new ItemKit();
            item.setKit(kitPeca);
            item.setPeca(peca);
            item.setQuantidade(itemDto.quantidade());
            return item;
        }).toList());

        repository.persist(kitPeca);
        return kitPeca;
    }

    @Override
    @Transactional
    public void update(Long id, KitPecaRequestDTO dto) {
        KitPeca entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Kit de Peças não encontrado");
        }

        var fabricante = fabricanteRepository.findById(dto.fabricanteId());
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        entidade.setFabricante(fabricante);
        
        entidade.getItens().clear();
        for (var itemDto : dto.itens()) {
            var peca = pecaRepository.findById(itemDto.pecaId());
            if (peca == null) {
                throw new NotFoundException("Peça com ID " + itemDto.pecaId() + " não encontrada");
            }

            ItemKit item = new ItemKit();
            item.setKit(entidade);
            item.setPeca(peca);
            item.setQuantidade(itemDto.quantidade());
            entidade.getItens().add(item);
        }
    }

    @Override
    public void delete(Long id) {
        KitPeca entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Kit de Peças não encontrado");
        }

        repository.delete(entidade);
    }
}
