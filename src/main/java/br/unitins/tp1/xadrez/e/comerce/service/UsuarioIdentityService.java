package br.unitins.tp1.xadrez.e.comerce.service;

public interface UsuarioIdentityService {

    String createUser(String email, boolean admin);

    void updateUser(String keycloakId, String email, Boolean admin);

    void deleteUser(String keycloakId);
}
