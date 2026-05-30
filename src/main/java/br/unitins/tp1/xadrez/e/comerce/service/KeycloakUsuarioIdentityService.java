package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class KeycloakUsuarioIdentityService implements UsuarioIdentityService {

    @ConfigProperty(name = "app.keycloak.admin.server-url", defaultValue = "http://localhost:8180")
    String serverUrl;

    @ConfigProperty(name = "app.keycloak.admin.realm", defaultValue = "xadrez-e-comerce")
    String realm;

    @ConfigProperty(name = "app.keycloak.admin.client-id", defaultValue = "admin-cli")
    String clientId;

    @ConfigProperty(name = "app.keycloak.admin.client-secret", defaultValue = "")
    String clientSecret;

    @ConfigProperty(name = "app.keycloak.admin.username", defaultValue = "admin")
    String username;

    @ConfigProperty(name = "app.keycloak.admin.password", defaultValue = "admin")
    String password;

    @ConfigProperty(name = "app.keycloak.admin.default-user-password", defaultValue = "ChangeMe123!")
    String defaultUserPassword;

    @Override
    public String createUser(String email, boolean admin) {
        try (Keycloak keycloak = getClient()) {
            RealmResource realmResource = keycloak.realm(realm);
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(email);
            user.setEmail(email);
            user.setEmailVerified(true);

            Response response = realmResource.users().create(user);
            int status = response.getStatus();
            if (status != Response.Status.CREATED.getStatusCode()) {
                throw new WebApplicationException("Não foi possível criar usuário no Keycloak", status);
            }

            String createdId = CreatedResponseUtil.getCreatedId(response);
            definirSenhaInicial(realmResource, createdId);
            atualizarRoleAdmin(realmResource, createdId, admin);
            return createdId;
        }
    }

    @Override
    public void updateUser(String keycloakId, String email, Boolean admin) {
        if (keycloakId == null || keycloakId.isBlank()) {
            return;
        }

        try (Keycloak keycloak = getClient()) {
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(keycloakId);

            UserRepresentation user = userResource.toRepresentation();
            user.setEmail(email);
            user.setUsername(email);
            userResource.update(user);

            if (admin != null) {
                atualizarRoleAdmin(realmResource, keycloakId, admin);
            }
        }
    }

    @Override
    public void deleteUser(String keycloakId) {
        if (keycloakId == null || keycloakId.isBlank()) {
            return;
        }

        try (Keycloak keycloak = getClient()) {
            Response response = keycloak.realm(realm).users().delete(keycloakId);
            int status = response.getStatus();
            if (status != Response.Status.NO_CONTENT.getStatusCode()) {
                throw new WebApplicationException("Não foi possível remover usuário no Keycloak", status);
            }
        }
    }

    private Keycloak getClient() {
        KeycloakBuilder builder = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId(clientId)
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD);

        if (clientSecret != null && !clientSecret.isBlank()) {
            builder.clientSecret(clientSecret);
        }

        return builder.build();
    }

    private void definirSenhaInicial(RealmResource realmResource, String keycloakId) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(defaultUserPassword);
        credential.setTemporary(true);

        realmResource.users().get(keycloakId).resetPassword(credential);
    }

    private void atualizarRoleAdmin(RealmResource realmResource, String keycloakId, boolean admin) {
        UserResource userResource = realmResource.users().get(keycloakId);
        RoleRepresentation adminRole = realmResource.roles().get("ADMIN").toRepresentation();

        if (admin) {
            userResource.roles().realmLevel().add(List.of(adminRole));
            return;
        }

        userResource.roles().realmLevel().remove(List.of(adminRole));
    }
}
