package dio.dio_spring_security_jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// CLASSE RESPONSAVEL POR CENTRALIZAR TODA CONFIGURACAO DE SEGURANCA DA API
// ATRAVES DESSA CLASSE É POSSIVEL DEFINIR QUAIS ENDPOINTS SAO PUBLICOS E QUAIS SAO PRIVADOS
// ALEM DE DEFINIR QUAIS SAO OS TIPOS DE AUTORIZACAO NECESSARIOS PARA ACESSAR DETERMINADOS ENDPOINTS
// ESSA CLASSE EXTENDE WebSecurityConfigurerAdapter QUE É UMA CLASSE DO SPRING SECURITY QUE JA POSSUI
// ALGUMAS CONFIGURACOES PADROES PARA SEGURANCA WEB
// ATRAVES DESSA CLASSE É POSSIVEL SOBRESCREVER METODOS PARA CUSTOMIZAR A SEGURANCA DA APLICACAO
// ATRAVES DO METODO configure(HttpSecurity http) É POSSIVEL DEFINIR QUAIS ENDPOINTS SAO PUBLICOS E QUAIS SAO PRIVADOS
// ATRAVES DO METODO configure(AuthenticationManagerBuilder auth) É POSSIVEL DEFINIR COMO SERA FEITA A AUTENTICACAO DOS US
// ATRAVES DO METODO configure(WebSecurity web) É POSSIVEL DEFINIR QUAIS ENDPOINTS SERAO IGNORADOS PELO SPRING SECURITY
// ATRAVES DO METODO configure(ResourceServerSecurityConfigurer resources) É POSSIVEL DEFINIR COMO SERA FEITA A AUTORIZACAO DOS USUARIOS
// ATRAVES DO METODO configureGlobal(AuthenticationManagerBuilder auth) É POSSIVEL DEFINIR COMO SER
// A AUTENTICACAO DOS USUARIOS SERA FEITA

@Configuration
@EnableWebSecurity // habilitando web security
@EnableGlobalMethodSecurity(prePostEnabled = true) // metodos globais serão sempre validados com um pre-autorização
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Habilitando tipo de criptografia de senha
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // Liberando acesso ao swagger
    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    // Configuração de segurança
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.cors().and().csrf().disable()
                .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                // liberando acessos publicos
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                // requer autenticação para acessar os endpoints
                .antMatchers(HttpMethod.GET, "/users").hasAnyRole("USERS", "MANAGERS")
                .antMatchers("/managers").hasAnyRole("MANAGERS")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}