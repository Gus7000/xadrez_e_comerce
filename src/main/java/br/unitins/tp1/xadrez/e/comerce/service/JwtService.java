package br.unitins.tp1.xadrez.e.comerce.service;

import java.time.Instant;
import java.util.Set;

import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JwtService {

    private static final String ISSUER = "xadrez_e_comerce";

    @Inject
    JWTParser parser;

    public String gerarToken(Usuario usuario) {
        return Jwt.claims()
                .subject(usuario.getEmail())
                .issuer(ISSUER)
                .groups(Set.of(usuario.getPerfil().name()))
                .claim("perfil", usuario.getPerfil().name())
                .issuedAt(Instant.now())
                .sign();
    }

    public JsonWebToken validarToken(String token) throws ParseException {
        return parser.parse(token);
    }

    public boolean tokenValido(String token) {
        try {
            parser.parse(token);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public String extrairLogin(String token) throws ParseException {
        return validarToken(token).getSubject();
    }
}