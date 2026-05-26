package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.DTO.MeResponseDTO;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN", "CLIENTE"})
public class MeResource {

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    public Response me() {
        String email = securityIdentity.getPrincipal().getName();
        String perfil = securityIdentity.getRoles().contains("ADMIN") ? "ADMIN" : "CLIENTE";
        MeResponseDTO response = new MeResponseDTO(email, perfil);

        return Response.ok(response).build();
    }
}