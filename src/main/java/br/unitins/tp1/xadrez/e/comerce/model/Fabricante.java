package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Entity;

@Entity
public class Fabricante extends DefaultEntity {
    private String nome;
    private String cnpj;
    private String telefone;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
   
    
}
