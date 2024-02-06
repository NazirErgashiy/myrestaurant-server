package uz.pikosolutions.service.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.pikosolutions.service.Role;

@Data
@AllArgsConstructor
public class TokenUser {
    private Long id;
    private String login;
    private Role role;
}
