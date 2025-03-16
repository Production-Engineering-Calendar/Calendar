package ro.unibuc.hello.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "yourSuperSecretKeyWithAtLeast32CharactersLong"; 
    private static final long EXPIRATION_TIME = 86400000; 

    private final Key key;

    public JwtUtils() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    // ✅ Generează un token JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // ✅ Extrage username-ul din token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirat: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Token invalid: " + e.getMessage());
        } catch (SecurityException e) { 
            System.out.println("Semnătură JWT invalidă: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Eroare validare token: " + e.getMessage());
        }
        return false;
    }
}
