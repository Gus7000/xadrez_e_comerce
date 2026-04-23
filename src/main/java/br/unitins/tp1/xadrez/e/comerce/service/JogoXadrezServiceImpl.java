package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.repository.KitPecaRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.JogoXadrezRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.RelogioRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.TabuleiroRepository;
import br.unitins.tp1.xadrez.e.comerce.mapper.JogoXadrezMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class JogoXadrezServiceImpl implements JogoXadrezService {

    @Inject
    JogoXadrezRepository repository;

    @Inject
    KitPecaRepository kitPecaRepository;

    @Inject
    TabuleiroRepository tabuleiroRepository;

    @Inject
    RelogioRepository relogioRepository;

    @Override
    public List<JogoXadrez> findAll() {
        return repository.listAll();
    }

    @Override
    public JogoXadrez findById(Long id) {
        JogoXadrez jogoXadrez = repository.findById(id);

        if (jogoXadrez == null) {
            throw new NotFoundException("Jogo Completo não encontrado");
        }

        return jogoXadrez;
    }

    @Override
    public List<JogoXadrez> findByKitPeca(Long kitPecaId) {
        return repository.findByKitPeca(kitPecaId).list();
    }

    @Override
    public List<JogoXadrez> findByTabuleiro(Long tabuleiroId) {
        return repository.findByTabuleiro(tabuleiroId).list();
    }

    @Override
    public JogoXadrez create(JogoXadrezRequestDTO dto) {
        KitPeca kitPeca = kitPecaRepository.findById(dto.kitPecaId());
        Tabuleiro tabuleiro = tabuleiroRepository.findById(dto.tabuleiroId());
        Relogio relogio = dto.relogioId() != null ? relogioRepository.findById(dto.relogioId()) : null;

        if (kitPeca == null) {
            throw new NotFoundException("Kit de Peças não encontrado");
        }
        if (tabuleiro == null) {
            throw new NotFoundException("Tabuleiro não encontrado");
        }

        JogoXadrez jogoXadrez = JogoXadrezMapper.toEntity(dto, kitPeca, tabuleiro, relogio);
        repository.persist(jogoXadrez);
        return jogoXadrez;
    }

    @Override
    public void update(Long id, JogoXadrezRequestDTO dto) {
        JogoXadrez entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Jogo Completo não encontrado");
        }

        KitPeca kitPeca = kitPecaRepository.findById(dto.kitPecaId());
        Tabuleiro tabuleiro = tabuleiroRepository.findById(dto.tabuleiroId());
        Relogio relogio = dto.relogioId() != null ? relogioRepository.findById(dto.relogioId()) : null;

        if (kitPeca == null) {
            throw new NotFoundException("Kit de Peças não encontrado");
        }
        if (tabuleiro == null) {
            throw new NotFoundException("Tabuleiro não encontrado");
        }

        JogoXadrez jogoXadrez = JogoXadrezMapper.toEntity(dto, kitPeca, tabuleiro, relogio);

        entidade.setNome(jogoXadrez.getNome());
        entidade.setPreco(jogoXadrez.getPreco());
        entidade.setDescricao(jogoXadrez.getDescricao());
        entidade.setEstoqueDisponivel(jogoXadrez.getEstoqueDisponivel());
        entidade.setKitPeca(jogoXadrez.getKitPeca());
        entidade.setTabuleiro(jogoXadrez.getTabuleiro());
        entidade.setRelogio(jogoXadrez.getRelogio());
    }

    @Override
    public void delete(Long id) {
        JogoXadrez entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Jogo Completo não encontrado");
        }

        repository.delete(entidade);
    }
}
