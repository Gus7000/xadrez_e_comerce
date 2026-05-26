package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

    public Pagamento findByPedidoId(Long pedidoId) {
        return find("pedido.id", pedidoId).firstResult();
    }

    public java.util.List<Pagamento> findByUsuarioId(Long usuarioId) {
        return find("pedido.usuario.id", usuarioId).list();
    }
}
