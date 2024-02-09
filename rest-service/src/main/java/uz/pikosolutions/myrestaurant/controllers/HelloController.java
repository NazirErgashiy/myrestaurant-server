package uz.pikosolutions.myrestaurant.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/hello", produces = "application/json")
public class HelloController {

    @Secured("ROLE_USER")
    //@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/hi")
    public String hi() {
        return "Hello";
    }
}
