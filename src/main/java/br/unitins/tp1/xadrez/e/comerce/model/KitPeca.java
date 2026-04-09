package br.unitins.tp1.xadrez.e.comerce.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class KitPeca extends DefaultEntity {
   @OneToMany(mappedBy = "kit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemKit> itens;
   @ManyToOne
   @JoinColumn(name = "id_fabricante")
    private Fabricante fabricante;

   public List<ItemKit> getItens() {
    return itens;
   }

   public void setItens(List<ItemKit> itens) {
    this.itens = itens;
   }
   public Fabricante getFabricante() {
    return fabricante;
   }
   public void setFabricante(Fabricante fabricante) {
    this.fabricante = fabricante;
   }
}
