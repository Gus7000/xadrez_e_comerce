package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PagamentoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.repository.PagamentoRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.PedidoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    @Inject
    PagamentoRepository repository;

    @Inject
    PedidoRepository pedidoRepository;

    @Override
    public List<Pagamento> findAll() {
        return repository.listAll(Sort.by("id"));
    }

    @Override
    public Pagamento findById(Long id) {
        Pagamento pagamento = repository.findById(id);

        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }

        return pagamento;
    }

    @Override
    public Pagamento findByPedidoId(Long pedidoId) {
        Pagamento pagamento = repository.findByPedidoId(pedidoId);

        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }

        return pagamento;
    }

    @Override
    public Pagamento create(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.pedidoId());

        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        Pagamento existente = repository.findByPedidoId(dto.pedidoId());
        if (existente != null) {
            throw new WebApplicationException("Já existe pagamento para este pedido", Response.Status.CONFLICT);
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodo(dto.metodo());
        pagamento.setStatus(dto.status());
        pagamento.setIdentificadorTransacao(dto.identificadorTransacao());
        pagamento.setValor(dto.valor());

        repository.persist(pagamento);
        return pagamento;
    }

    @Override
    public void update(Long id, PagamentoRequestDTO dto) {
        Pagamento pagamento = repository.findById(id);

        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }

        Pedido pedido = pedidoRepository.findById(dto.pedidoId());

        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        Pagamento existente = repository.findByPedidoId(dto.pedidoId());
        if (existente != null && !existente.getId().equals(id)) {
            throw new WebApplicationException("Já existe pagamento para este pedido", Response.Status.CONFLICT);
        }

        pagamento.setPedido(pedido);
        pagamento.setMetodo(dto.metodo());
        pagamento.setStatus(dto.status());
        pagamento.setIdentificadorTransacao(dto.identificadorTransacao());
        pagamento.setValor(dto.valor());
    }

    @Override
    public void delete(Long id) {
        Pagamento pagamento = repository.findById(id);

        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }

        repository.delete(pagamento);
    }

    @Override
    public java.util.List<Pagamento> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    @Override
    public void estornar(Long id) {
        Pagamento pagamento = repository.findById(id);
        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }
        pagamento.setStatus(br.unitins.tp1.xadrez.e.comerce.model.PagamentoStatus.ESTORNADO);
    }
}
