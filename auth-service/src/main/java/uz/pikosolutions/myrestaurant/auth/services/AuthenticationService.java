package uz.pikosolutions.myrestaurant.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pikosolutions.myrestaurant.auth.configs.jwt.JwtService;
import uz.pikosolutions.myrestaurant.auth.dto.request.AuthenticationRequest;
import uz.pikosolutions.myrestaurant.auth.dto.request.RegisterRequest;
import uz.pikosolutions.myrestaurant.auth.dto.response.AuthenticationResponse;
import uz.pikosolutions.myrestaurant.auth.entities.Role;
import uz.pikosolutions.myrestaurant.auth.entities.User;
import uz.pikosolutions.myrestaurant.auth.error.exceptions.ForbiddenException;
import uz.pikosolutions.myrestaurant.auth.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        if (userRepository.findByName(request.getName()).isEmpty()) {
            userRepository.save(user);
        } else throw new ForbiddenException("Name is already used");

        var token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getName(),
                request.getPassword()
        ));

        var user = userRepository.findByName(request.getName())
                .orElseThrow();//TODO optional -> implement throwing exception

        var token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }
}
