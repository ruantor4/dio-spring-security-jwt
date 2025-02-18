package dio.dio_spring_security_jwt.security;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

// CLASSE QUE REPRESENTARÁ UM OBJETO PARA GERAR TOKEN JWT.

public class JWTObject {

    private String subject; // nome do usuario
    private Date issuedAt; // data de emissao
    private Date expiration; // data de expiracao
    private List<String> roles; // perfis de acesso

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    // Define os papéis (roles) do usuário.
    public void setRoles(String... roles) {
        // Converte os argumentos em uma lista imutável
        this.roles = Arrays.asList(roles);
    }

}