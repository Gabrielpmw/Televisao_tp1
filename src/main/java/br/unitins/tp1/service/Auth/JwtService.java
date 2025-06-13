package br.unitins.tp1.service.Auth;

public interface JwtService {
    String generateJwt(String username, String perfil);
}
