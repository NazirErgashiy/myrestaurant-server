package uz.pikosolutions.myrestaurant.auth.controllers;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.pikosolutions.myrestaurant.auth.dto.request.AuthenticationRequest;
import uz.pikosolutions.myrestaurant.auth.dto.request.RegisterRequest;
import uz.pikosolutions.myrestaurant.auth.dto.response.AuthenticationResponse;
import uz.pikosolutions.myrestaurant.auth.rest.UserRestTemplate;
import uz.pikosolutions.myrestaurant.auth.services.AuthenticationService;

@RestController
@RequestMapping(value = "/api/v1/auth", produces = "application/json")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRestTemplate userRestTemplate;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(
            @RequestBody
            @Validated
            RegisterRequest request
    ) {
        AuthenticationResponse response = authenticationService.register(request);
        userRestTemplate.createUserInRestService(response.getToken());
        return response;
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticate(
            @RequestBody
            @Validated
            AuthenticationRequest request
    ) {
        return authenticationService.authenticate(request);
    }

    @GetMapping("/hello")
    public Response hello() {
        Response response=new Response();
        response.setContent("Hello");
        return response;
    }
}

@Data
class Response{
    String content;
}