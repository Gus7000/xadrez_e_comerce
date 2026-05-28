package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;
import br.unitins.tp1.xadrez.e.comerce.repository.CupomDescontoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CupomDescontoServiceImpl implements CupomDescontoService {

    @Inject
    CupomDescontoRepository repository;

    @Override
    public List<CupomDesconto> findAll() {
        return repository.listAll(Sort.by("id"));
    }

    @Override
    public CupomDesconto findById(Long id) {
        CupomDesconto c = repository.findById(id);
        if (c == null) throw new NotFoundException("Cupom não encontrado");
        return c;
    }

    @Override
    public CupomDesconto findByCodigo(String codigo) {
        CupomDesconto c = repository.find("codigo", codigo).firstResult();
        if (c == null) throw new NotFoundException("Cupom não encontrado");
        return c;
    }

    @Override
    public CupomDesconto create(CupomDesconto cupom) {
        repository.persist(cupom);
        return cupom;
    }

    @Override
    public void update(Long id, CupomDesconto cupom) {
        CupomDesconto entidade = repository.findById(id);
        if (entidade == null) throw new NotFoundException("Cupom não encontrado");

        entidade.setCodigo(cupom.getCodigo());
        entidade.setDataValidade(cupom.getDataValidade());
        entidade.setAtivo(cupom.isAtivo());
        entidade.setUsoMaximo(cupom.getUsoMaximo());
        entidade.setPorUsuario(cupom.isPorUsuario());
        // campos específicos (FIXO / PERCENTUAL) devem ser tratados externamente se necessário
    }

    @Override
    public void delete(Long id) {
        CupomDesconto entidade = repository.findById(id);
        if (entidade == null) throw new NotFoundException("Cupom não encontrado");
        repository.delete(entidade);
    }
}
