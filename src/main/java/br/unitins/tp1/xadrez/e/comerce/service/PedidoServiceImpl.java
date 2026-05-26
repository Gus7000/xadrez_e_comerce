package br.unitins.tp1.xadrez.e.comerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoItemRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDescontoFixo;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDescontoPercentual;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.PedidoItem;
import br.unitins.tp1.xadrez.e.comerce.model.PedidoStatus;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.CupomDescontoRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.JogoXadrezRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.PedidoItemRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.PedidoRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.UsuarioRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository repository;

    @Inject
    PedidoItemRepository pedidoItemRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JogoXadrezRepository jogoXadrezRepository;

    @Inject
    CupomDescontoRepository cupomRepository;

    @Override
    public List<Pedido> findAll() {
        return repository.listAll(Sort.by("id"));
    }

    @Override
    public Pedido findById(Long id) {
        Pedido pedido = repository.findById(id);

        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        return pedido;
    }

    @Override
    public List<Pedido> findByUsuarioId(Long usuarioId) {
        return repository.find("usuario.id = ?1 order by id", usuarioId).list();
    }

    @Override
    public Pedido create(PedidoRequestDTO dto) {
        validarItens(dto.items());

        Usuario usuario = usuarioRepository.findById(dto.usuarioId());

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        if (!usuario.isCadastroCompleto()) {
            throw new WebApplicationException(
                    "Você precisa completar seu cadastro antes de fazer um pedido",
                    Response.Status.PRECONDITION_FAILED);
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setStatus(dto.status() != null ? dto.status() : PedidoStatus.AGUARDANDO_PAGAMENTO);

        CupomDesconto cupom = null;
        if (dto.cupomId() != null) {
            cupom = cupomRepository.findById(dto.cupomId());
            if (cupom == null) {
                throw new NotFoundException("Cupom não encontrado");
            }
            pedido.setCupom(cupom);
        }

        repository.persist(pedido);

        List<PedidoItem> items = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (PedidoItemRequestDTO itemDto : dto.items()) {
            JogoXadrez jogo = jogoXadrezRepository.findById(itemDto.jogoId());

            if (jogo == null) {
                throw new NotFoundException("Jogo não encontrado");
            }

            if (itemDto.quantidade() <= 0) {
                throw new WebApplicationException("Quantidade deve ser maior que zero", Response.Status.BAD_REQUEST);
            }

            BigDecimal precoUnitario = itemDto.precoUnitario() != null
                    ? itemDto.precoUnitario()
                    : BigDecimal.valueOf(jogo.getPreco());

            PedidoItem item = new PedidoItem();
            item.setPedido(pedido);
            item.setJogo(jogo);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(precoUnitario);

            pedidoItemRepository.persist(item);
            items.add(item);

            subtotal = subtotal.add(precoUnitario.multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        pedido.setItems(items);
        pedido.setSubtotal(subtotal);

        BigDecimal desconto = aplicarDesconto(cupom, subtotal);
        pedido.setDesconto(desconto);

        BigDecimal frete = BigDecimal.ZERO;
        BigDecimal taxas = BigDecimal.ZERO;

        pedido.setFrete(frete);
        pedido.setTaxas(taxas);
        pedido.setValorTotal(subtotal.subtract(desconto).add(frete).add(taxas));

        return pedido;
    }

    @Override
    public void updateStatus(Long id, PedidoStatus status) {
        Pedido pedido = repository.findById(id);

        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        pedido.setStatus(status);
    }

    @Override
    public void delete(Long id) {
        Pedido pedido = repository.findById(id);

        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        repository.delete(pedido);
    }

    private void validarItens(List<PedidoItemRequestDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new WebApplicationException("Pedido deve conter ao menos um item", Response.Status.BAD_REQUEST);
        }
    }

    private BigDecimal aplicarDesconto(CupomDesconto cupom, BigDecimal subtotal) {
        if (cupom == null) {
            return BigDecimal.ZERO;
        }

        if (cupom instanceof CupomDescontoFixo fixo) {
            BigDecimal valorDesconto = fixo.getValorDesconto() != null ? fixo.getValorDesconto() : BigDecimal.ZERO;
            return valorDesconto.min(subtotal);
        }

        if (cupom instanceof CupomDescontoPercentual percentual) {
            Double percentualDesconto = percentual.getPercentualDesconto();
            if (percentualDesconto == null || percentualDesconto <= 0) {
                return BigDecimal.ZERO;
            }

            BigDecimal desconto = subtotal.multiply(BigDecimal.valueOf(percentualDesconto / 100.0));
            return desconto.min(subtotal);
        }

        return BigDecimal.ZERO;
    }
}
