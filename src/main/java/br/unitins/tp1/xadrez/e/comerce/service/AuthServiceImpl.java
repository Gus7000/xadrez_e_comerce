package br.unitins.tp1.xadrez.e.comerce.service;

import br.unitins.tp1.xadrez.e.comerce.DTO.AuthRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.AuthResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(dto.login());

        if (usuario == null || !hashService.verify(dto.senha(), usuario.getSenhaHash())) {
            throw new NotAuthorizedException("Login ou senha inválidos");
        }

        String token = jwtService.gerarToken(usuario);
        return new AuthResponseDTO(usuario.getLogin(), token, usuario.getPerfil());
    }
}