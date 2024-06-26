package uz.pikosolutions.myrestaurant.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Size(min = 3, max = 15, message = "Name size limits [3-15]")
    private String login;
    @Size(min = 3, max = 30)
    private String password;
}
