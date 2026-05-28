package br.unitins.tp1.xadrez.e.comerce.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lista_desejos")
public class ListaDesejos extends DefaultEntity {

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lista_desejos_jogo",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "jogo_id"))
    private Set<JogoXadrez> jogos = new HashSet<>();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<JogoXadrez> getJogos() {
        return jogos;
    }

    public void setJogos(Set<JogoXadrez> jogos) {
        this.jogos = jogos;
    }
}
