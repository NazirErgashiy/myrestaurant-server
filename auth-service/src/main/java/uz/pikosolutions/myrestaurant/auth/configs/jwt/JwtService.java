package uz.pikosolutions.myrestaurant.auth.configs.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pikosolutions.service.TokenData;
import uz.pikosolutions.service.entity.AuthUser;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.expiration.time.seconds}")
    private int jwtTokenExpirationTime;

    public String getToken(final AuthUser user) {
        final Map<String, Object> tokenData = new HashMap<>();
        tokenData.put(TokenData.ID.name(), user.getId());
        tokenData.put(TokenData.LOGIN.name(), user.getLogin());
        tokenData.put(TokenData.ROLE.name(), user.getRole());
        tokenData.put(TokenData.CREATE_DATE.name(), new Date().getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, jwtTokenExpirationTime);
        tokenData.put(TokenData.EXPIRATION_DATE.name(), calendar.getTime());
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.setClaims(tokenData);
        return jwtBuilder.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
