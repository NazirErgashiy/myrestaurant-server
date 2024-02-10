package uz.pikosolutions.myrestaurant.auth.rest;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uz.pikosolutions.service.jwt.TokenAuthentication;
import uz.pikosolutions.service.jwt.TokenService;

import java.net.URI;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserRestTemplate {
    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public void createUserInRestService(String token) {
        TokenAuthentication authentication = tokenService.parseAndCheckToken(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        //HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        //restTemplate.postForObject("http://localhost:8092/api/v1/users", entity, String.class);

        User user = new User();
        user.setName(authentication.getName());
        //System.out.println(authentication);
        //restTemplate.postForEntity("http://localhost:8092/api/v1/users", user, User.class);

        RequestEntity<User> request = RequestEntity.post(URI.create("http://localhost:8092/api/v1/users"))
                .contentType(MediaType.APPLICATION_JSON)
                .headers(
                        new HttpHeaders() {{
                            setBearerAuth(token);
                        }})
                .body(user);
        System.out.println(request);
        restTemplate.exchange(request, String.class);
    }
}

@Data
class User {
    private Long id;
    private String name;
}