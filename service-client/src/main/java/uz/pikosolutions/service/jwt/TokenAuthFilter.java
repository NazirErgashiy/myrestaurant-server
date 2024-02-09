package uz.pikosolutions.service.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pikosolutions.service.error.dto.ExceptionResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

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
            final TokenAuthentication authentication = tokenService.parseAndCheckToken(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //filterChain.doFilter(request, response);
        } catch (Exception e) {
            ExceptionResponse exceptionResponse = ExceptionResponse
                    .builder()
                    .message(e.getMessage())
                    .code("UNAUTHORIZED")
                    .timestamp(LocalDateTime.now())
                    .build();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }
}
