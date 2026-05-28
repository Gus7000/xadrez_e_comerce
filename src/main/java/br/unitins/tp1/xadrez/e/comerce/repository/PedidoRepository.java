package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

	public Pedido findByUsuarioAndId(Long usuarioId, Long id) {
		return find("usuario.id = ?1 and id = ?2", usuarioId, id).firstResult();
	}

	public long countByUsuarioAndCupom(Usuario usuario, CupomDesconto cupom) {
		return count("usuario = ?1 and cupom = ?2", usuario, cupom);
	}

}
