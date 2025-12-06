package br.unitins.tp1.service.Auth;

import jakarta.enterprise.context.ApplicationScoped;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@ApplicationScoped
public class HashServiceImpl implements HashService{

    private String salt = "@#1237Zt";
    private Integer iterationCount = 403;
    private Integer keyLength = 512;

    @Override
    public String getHashSenha(String senha){
        byte[] result;
        try {
            result = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(senha.toCharArray(),
                            salt.getBytes(),
                            iterationCount,
                            keyLength))
                    .getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new RuntimeException("Problema ao gerar o hash");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Problema ao gerar o hash");
        }

        return Base64.getEncoder().encodeToString(result);
    }

    public boolean verificarSenha(String senha, String hash) {
        // Gera o hash da senha recebida e compara com o hash do banco
        return getHashSenha(senha).equals(hash);
    }
}
