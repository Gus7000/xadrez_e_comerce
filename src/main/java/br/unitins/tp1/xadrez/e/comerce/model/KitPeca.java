package br.unitins.tp1.xadrez.e.comerce.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class KitPeca extends DefaultEntity {
   @OneToMany(mappedBy = "kit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemKit> itens;

   public List<ItemKit> getItens() {
    return itens;
   }

   public void setItens(List<ItemKit> itens) {
    this.itens = itens;
   }
    
    
}
