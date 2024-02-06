package uz.pikosolutions.myrestaurant.auth.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pikosolutions.myrestaurant.auth.entities.User;
import uz.pikosolutions.myrestaurant.auth.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User loadUserByName(String email) throws UsernameNotFoundException {
        return userRepository.findByLogin(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
