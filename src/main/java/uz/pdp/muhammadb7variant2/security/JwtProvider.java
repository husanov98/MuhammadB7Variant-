package uz.pdp.muhammadb7variant2.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.muhammadb7variant2.entity.Role;

import java.util.Date;
import java.util.Set;

//import static uz.mh.util.MyUtil.*;
@Component
public class JwtProvider {

    private static final long expireTime = 1000 * 60 * 60 * 24 * 7;
    private static final String secretKey = "secret";

    public String generateToken(String email, Set<Role> roles) {
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        String token = Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
        return token;
    }
}
