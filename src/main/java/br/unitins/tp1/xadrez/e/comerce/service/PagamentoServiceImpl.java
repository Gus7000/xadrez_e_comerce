package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PagamentoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoBoleto;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoCartaoCredito;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoCartaoDebito;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoPix;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.repository.PagamentoRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.PedidoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    private static final String TIPO_PIX = "PIX";
    private static final String TIPO_CARTAO_CREDITO = "CARTAO_CREDITO";
    private static final String TIPO_CARTAO_DEBITO = "CARTAO_DEBITO";
    private static final String TIPO_BOLETO = "BOLETO";

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
    @Transactional
    public Pagamento create(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.pedidoId());

        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado");
        }

        Pagamento existente = repository.findByPedidoId(dto.pedidoId());
        if (existente != null) {
            throw new WebApplicationException("Já existe pagamento para este pedido", Response.Status.CONFLICT);
        }

        Pagamento pagamento = criarPagamento(dto);
        pagamento.setPedido(pedido);
        preencherCamposComuns(pagamento, dto);

        repository.persist(pagamento);
        return pagamento;
    }

    @Override
    @Transactional
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
        Pagamento novoTipo = criarPagamento(dto);
        if (!pagamento.getClass().equals(novoTipo.getClass())) {
            throw new WebApplicationException("Não é permitido alterar o tipo do pagamento", Response.Status.BAD_REQUEST);
        }

        preencherCamposComuns(pagamento, dto);
        preencherCamposEspecificos(pagamento, dto);
    }

    @Override
    @Transactional
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
    @Transactional
    public void estornar(Long id) {
        Pagamento pagamento = repository.findById(id);
        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }
        pagamento.setStatus(br.unitins.tp1.xadrez.e.comerce.model.PagamentoStatus.ESTORNADO);
    }

    private Pagamento criarPagamento(PagamentoRequestDTO dto) {
        if (dto.tipo() == null) {
            throw new WebApplicationException("tipo é obrigatório", Response.Status.BAD_REQUEST);
        }

        String tipo = dto.tipo().trim().toUpperCase();

        return switch (tipo) {
            case TIPO_PIX -> {
                PagamentoPix pix = new PagamentoPix();
                pix.setChavePix(dto.chavePix());
                yield pix;
            }
            case TIPO_CARTAO_CREDITO -> {
                PagamentoCartaoCredito credito = new PagamentoCartaoCredito();
                credito.setBandeira(dto.bandeira());
                credito.setParcelas(dto.parcelas());
                credito.setNomeTitular(dto.nomeTitular());
                credito.setUltimosDigitos(dto.ultimosDigitos());
                yield credito;
            }
            case TIPO_CARTAO_DEBITO -> {
                PagamentoCartaoDebito debito = new PagamentoCartaoDebito();
                debito.setBanco(dto.banco());
                debito.setNomeTitular(dto.nomeTitular());
                debito.setUltimosDigitos(dto.ultimosDigitos());
                yield debito;
            }
            case TIPO_BOLETO -> {
                PagamentoBoleto boleto = new PagamentoBoleto();
                boleto.setLinhaDigitavel(dto.linhaDigitavel());
                boleto.setDataVencimento(dto.dataVencimento());
                yield boleto;
            }
            default -> throw new WebApplicationException("Tipo de pagamento inválido", Response.Status.BAD_REQUEST);
        };
    }

    private void preencherCamposComuns(Pagamento pagamento, PagamentoRequestDTO dto) {
        pagamento.setStatus(dto.status());
        pagamento.setIdentificadorTransacao(dto.identificadorTransacao());
        pagamento.setValor(dto.valor());
        pagamento.setDataConfirmacao(dto.dataConfirmacao());
    }

    private void preencherCamposEspecificos(Pagamento pagamento, PagamentoRequestDTO dto) {
        if (pagamento instanceof PagamentoPix pix) {
            pix.setChavePix(dto.chavePix());
        } else if (pagamento instanceof PagamentoCartaoCredito credito) {
            credito.setBandeira(dto.bandeira());
            credito.setParcelas(dto.parcelas());
            credito.setNomeTitular(dto.nomeTitular());
            credito.setUltimosDigitos(dto.ultimosDigitos());
        } else if (pagamento instanceof PagamentoCartaoDebito debito) {
            debito.setBanco(dto.banco());
            debito.setNomeTitular(dto.nomeTitular());
            debito.setUltimosDigitos(dto.ultimosDigitos());
        } else if (pagamento instanceof PagamentoBoleto boleto) {
            boleto.setLinhaDigitavel(dto.linhaDigitavel());
            boleto.setDataVencimento(dto.dataVencimento());
        }
    }
}
