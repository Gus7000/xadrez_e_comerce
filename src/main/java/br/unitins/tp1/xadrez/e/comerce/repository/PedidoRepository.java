package br.unitins.tp1.xadrez.e.comerce.repository;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

	@Inject
	EntityManager entityManager;

	public List<Pedido> findPageAllWithItems(int page, int size) {
		List<Long> ids = entityManager.createQuery("SELECT p.id FROM Pedido p ORDER BY p.id", Long.class)
				.setFirstResult(page * size)
				.setMaxResults(size)
				.getResultList();

		return fetchByIdsWithItems(ids);
	}

	public List<Pedido> findPageByUsuarioIdWithItems(Long usuarioId, int page, int size) {
		List<Long> ids = entityManager.createQuery("SELECT p.id FROM Pedido p WHERE p.usuario.id = :usuarioId ORDER BY p.id", Long.class)
				.setParameter("usuarioId", usuarioId)
				.setFirstResult(page * size)
				.setMaxResults(size)
				.getResultList();

		return fetchByIdsWithItems(ids);
	}

	public Pedido findByIdWithItems(Long id) {
		return find("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.items i LEFT JOIN FETCH i.jogo WHERE p.id = ?1", id)
				.firstResult();
	}

	private List<Pedido> fetchByIdsWithItems(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return List.of();
		}

		return find("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.items i LEFT JOIN FETCH i.jogo WHERE p.id IN ?1 ORDER BY p.id", ids)
				.list();
	}

	public Pedido findByUsuarioAndId(Long usuarioId, Long id) {
		return find("usuario.id = ?1 and id = ?2", usuarioId, id).firstResult();
	}

	public long countByUsuarioAndCupom(Usuario usuario, CupomDesconto cupom) {
		return count("usuario = ?1 and cupom = ?2", usuario, cupom);
	}

}
