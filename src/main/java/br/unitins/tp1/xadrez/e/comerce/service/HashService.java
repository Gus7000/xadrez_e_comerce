package br.unitins.tp1.xadrez.e.comerce.service;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashService {

    public String hash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public boolean verify(String senha, String senhaHash) {
        return BCrypt.checkpw(senha, senhaHash);
    }
}