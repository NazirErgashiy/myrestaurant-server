package uz.pikosolutions.service.jwt;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            jwt = authHeader.substring(7);
            log.info(jwt);
            final TokenAuthentication authentication = tokenService.parseAndCheckToken(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(SecurityContextHolder.getContext().getAuthentication().toString());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
