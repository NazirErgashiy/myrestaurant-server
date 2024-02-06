package uz.pikosolutions.myrestaurant.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pikosolutions.service.jwt.TokenAuthFilter;
import uz.pikosolutions.service.jwt.TokenService;
import uz.pikosolutions.service.listener.ApplicationEventListener;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Value("${jwt.secret.key}")
    private String secretKey;
    private final Environment environment;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Print starter info
    @Bean
    public ApplicationEventListener applicationEventListener() {
        return new ApplicationEventListener(environment);
    }

    @Bean
    public TokenAuthFilter tokenAuthFilter() {
        TokenAuthFilter tokenAuthFilter = new TokenAuthFilter(tokenService());
        return tokenAuthFilter;
    }

    @Bean
    public TokenService tokenService() {
        TokenService tokenService = new TokenService();
        tokenService.setSecretKey(secretKey);
        return tokenService;
    }
}
