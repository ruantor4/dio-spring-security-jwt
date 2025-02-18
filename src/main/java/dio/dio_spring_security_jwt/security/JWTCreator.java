package dio.dio_spring_security_jwt.security;

import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

// CLASSE RESPONSAVEL POR GERAR O TOKEN JWT COM BASE NO OBJETO E VICE-VERSA

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    // pega atributos do objeto e cria um token JWT

    public static String create(String prefix, String key, JWTObject jwtObject) {
        // Cria o token JWT
        String token = Jwts.builder().setSubject(jwtObject.getSubject()).setIssuedAt(jwtObject.getIssuedAt())
                .setExpiration(jwtObject.getExpiration())
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles())).signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return prefix + " " + token;
    }

    // pega o token JWT verificar prefixo e chave e cria um objeto JWTObject

    public static JWTObject create(String token, String prefix, String key)
            // OBS: O metodo create(String token,String prefix,String key) lanca as
            // excecoes:ExpiredJwtException, UnsupportedJwtException
            // MalformedJwtException, SignatureException
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        JWTObject object = new JWTObject();
        token = token.replace(prefix, "");
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        object.setSubject(claims.getSubject());
        object.setExpiration(claims.getExpiration());
        object.setIssuedAt(claims.getIssuedAt());
        object.setRoles((List) claims.get(ROLES_AUTHORITIES));
        return object;

    }

    // checkRoles(List<String> roles): List<String>

    private static List<String> checkRoles(List<String> roles) {
        // Verifica se os roles possuem o prefixo ROLE_
        return roles.stream().map(s -> "ROLE_".concat(s.replaceAll("ROLE_", ""))).collect(Collectors.toList());
    }

}