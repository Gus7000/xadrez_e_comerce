package br.unitins.tp1.xadrez.e.comerce.repository;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ListaDesejosRepository implements PanacheRepository<ListaDesejos> {

	public List<ListaDesejos> findAllWithJogos() {
		return find("SELECT DISTINCT l FROM ListaDesejos l LEFT JOIN FETCH l.jogos ORDER BY l.id").list();
	}

	public ListaDesejos findByIdWithJogos(Long id) {
		return find("SELECT DISTINCT l FROM ListaDesejos l LEFT JOIN FETCH l.jogos WHERE l.id = ?1", id)
				.firstResult();
	}

	public List<ListaDesejos> findByUsuarioIdWithJogos(Long usuarioId) {
		return find("SELECT DISTINCT l FROM ListaDesejos l LEFT JOIN FETCH l.jogos WHERE l.usuario.id = ?1 ORDER BY l.id",
				usuarioId).list();
	}

	public ListaDesejos findFirstByUsuarioIdWithJogos(Long usuarioId) {
		return find("SELECT DISTINCT l FROM ListaDesejos l LEFT JOIN FETCH l.jogos WHERE l.usuario.id = ?1 ORDER BY l.id",
				usuarioId).firstResult();
	}

}
