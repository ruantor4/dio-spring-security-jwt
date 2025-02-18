package dio.dio_spring_security_jwt.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// CLASSE COMPONENTE QUE RECEBERA AS PROPRIEDADES E CREDENCIAIS DO TOKEN VIA APPLICATION.PROPERTIES
// E AS DISPONIBILIZARA PARA A APLICACAO

@Configuration
@ConfigurationProperties(prefix = "security.config") // prefixo exisitente no application.properties
public class SecurityConfig {
    public static String PREFIX; // prefixo do token
    public static String KEY; // chave de seguranca privada
    public static Long EXPIRATION; // tempo de expiracao do token

    public void setPrefix(String prefix) {
        PREFIX = prefix;
    }

    public void setKey(String key) {
        KEY = key;
    }

    public void setExpiration(Long expiration) {
        EXPIRATION = expiration;
    }
}
