package uz.pikosolutions.myrestaurant.auth.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import uz.pikosolutions.service.jwt.TokenAuthFilter;
import uz.pikosolutions.service.jwt.TokenService;
import uz.pikosolutions.service.listener.ApplicationEventListener;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Value("${jwt.secret.key}")
    private String secretKey;
    private final UserDetailsServiceImpl userDetailsService;
    private final Environment environment;

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper
                .builder()
                .findAndAddModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

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
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    @Bean
    public TokenService tokenService() {
        TokenService tokenService = new TokenService();
        tokenService.setSecretKey(secretKey);
        return tokenService;
    }
}
