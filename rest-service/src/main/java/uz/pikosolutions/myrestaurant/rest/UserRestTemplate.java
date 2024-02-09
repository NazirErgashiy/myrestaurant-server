package uz.pikosolutions.myrestaurant.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uz.pikosolutions.service.entity.AuthUser;
import uz.pikosolutions.service.jwt.TokenAuthentication;

@Component
@RequiredArgsConstructor
public class UserRestTemplate {

    private final RestTemplate restTemplate;

    public AuthUser getUserById(Long id) {
        //return restTemplate.getForEntity("localhost:8091/api/v1/users/"+id, User.class).getBody();

        TokenAuthentication authentication = (TokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authentication.getToken());
        //headers.set("Other-Header", "othervalue");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<AuthUser> response = restTemplate.exchange("http://localhost:8090/auth-service/api/v1/users/" + id, HttpMethod.GET, requestEntity, AuthUser.class);
        return response.getBody();
    }
}
