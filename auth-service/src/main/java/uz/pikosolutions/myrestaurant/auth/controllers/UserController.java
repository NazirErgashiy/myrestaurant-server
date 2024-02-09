package uz.pikosolutions.myrestaurant.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pikosolutions.myrestaurant.auth.services.UserService;
import uz.pikosolutions.service.entity.AuthUser;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public AuthUser getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
