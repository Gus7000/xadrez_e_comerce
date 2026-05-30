package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

@ApplicationScoped
public class KeycloakAdminService {

    private static final String ADMIN_ROLE_NAME = "ADMIN";

    @Inject
    Keycloak keycloak;

    @ConfigProperty(name = "quarkus.keycloak.admin-client.realm")
    String realm;

    @ConfigProperty(name = "quarkus.keycloak.admin-client.default-user-password", defaultValue = "ChangeMe123!")
    String defaultUserPassword;

    public String createUser(String nome, String email, String senha) {
        validatePassword(senha);

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(email);
        user.setEmail(email);
        user.setEmailVerified(true);
        if (nome != null && !nome.isBlank()) {
            user.setFirstName(nome);
        }

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(senha);
        credential.setTemporary(false);
        user.setCredentials(List.of(credential));

        try (Response response = keycloak.realm(realm).users().create(user)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new WebApplicationException(
                        "Não foi possível criar usuário no Keycloak",
                        response.getStatus());
            }

            return CreatedResponseUtil.getCreatedId(response);
        }
    }

    public String createUser(String email) {
        return createUser(email, email, defaultUserPassword);
    }

    public void deletarUsuario(String keycloakUserId) {
        if (keycloakUserId == null || keycloakUserId.isBlank()) {
            return;
        }

        try (Response response = keycloak.realm(realm).users().delete(keycloakUserId)) {
            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                throw new WebApplicationException(
                        "Não foi possível remover usuário no Keycloak",
                        response.getStatus());
            }
        }
    }

    public void promoverParaAdmin(String keycloakUserId) {
        if (keycloakUserId == null || keycloakUserId.isBlank()) {
            return;
        }

        RoleRepresentation adminRole = keycloak.realm(realm).roles().get(ADMIN_ROLE_NAME).toRepresentation();
        keycloak.realm(realm).users().get(keycloakUserId).roles().realmLevel().add(List.of(adminRole));
    }

    public void removerRoleAdmin(String keycloakUserId) {
        if (keycloakUserId == null || keycloakUserId.isBlank()) {
            return;
        }

        RoleRepresentation adminRole = keycloak.realm(realm).roles().get(ADMIN_ROLE_NAME).toRepresentation();
        keycloak.realm(realm).users().get(keycloakUserId).roles().realmLevel().remove(List.of(adminRole));
    }

    public UserRepresentation obterUsuario(String keycloakUserId) {
        if (keycloakUserId == null || keycloakUserId.isBlank()) {
            return null;
        }

        try {
            return keycloak.realm(realm).users().get(keycloakUserId).toRepresentation();
        } catch (NotFoundException e) {
            return null;
        }
    }

    public List<UserRepresentation> listarUsuarios() {
        return keycloak.realm(realm).users().list();
    }

    public int countUsersWithRealmRole(String roleName) {
        var roleResource = keycloak.realm(realm).roles().get(roleName);
        List<UserRepresentation> users = roleResource.getUserMembers();
        return users != null ? users.size() : 0;
    }

    public void updateUser(String keycloakId, String email) {
        if (keycloakId == null || keycloakId.isBlank()) {
            return;
        }

        UserRepresentation user = keycloak.realm(realm).users().get(keycloakId).toRepresentation();
        if (user == null) {
            throw new WebApplicationException("Usuário não encontrado no Keycloak", Response.Status.NOT_FOUND);
        }

        user.setEmail(email);
        user.setUsername(email);
        keycloak.realm(realm).users().get(keycloakId).update(user);
    }

    private void validatePassword(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new WebApplicationException("Senha do usuário é obrigatória", Response.Status.BAD_REQUEST);
        }
    }
}