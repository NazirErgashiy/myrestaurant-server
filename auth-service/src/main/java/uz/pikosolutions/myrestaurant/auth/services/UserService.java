package uz.pikosolutions.myrestaurant.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pikosolutions.myrestaurant.auth.repositories.UserRepository;
import uz.pikosolutions.service.entity.AuthUser;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public AuthUser getUserById(Long id) {
        return userRepository.findById(id).get();
    }
}
