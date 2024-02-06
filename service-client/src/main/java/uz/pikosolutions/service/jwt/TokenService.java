package uz.pikosolutions.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationServiceException;
import uz.pikosolutions.service.Role;
import uz.pikosolutions.service.TokenData;

import java.security.Key;
import java.util.Date;

public class TokenService {

    private String secretKey;

    public void setSecretKey(String key) {
        this.secretKey = key;
    }

    public TokenAuthentication parseAndCheckToken(final String token) {
        Claims claims;
        try {
            claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception ex) {
            throw new AuthenticationServiceException("Token corrupted");
        }

        if (claims.get(TokenData.EXPIRATION_DATE.name(), Long.class) == null) {
            throw new AuthenticationServiceException("Invalid token");
        }

        Date expiredDate = new Date(claims.get(TokenData.EXPIRATION_DATE.name(), Long.class));
        if (!expiredDate.after(new Date())) {
            throw new AuthenticationServiceException("Token expired date error");
        }

        Long id = claims.get(TokenData.ID.name(), Number.class).longValue();
        String login = claims.get(TokenData.LOGIN.name(), String.class);
        String role = claims.get(TokenData.ROLE.name(), String.class);

        TokenUser user = new TokenUser(id, login, Role.valueOf(role));

        return new TokenAuthentication(token, true, user);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
