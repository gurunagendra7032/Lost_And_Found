package college.project.demo.Service;

import college.project.demo.Entities.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {

    private static final String SECRET="W5WbcAQIEDrSoRuaJD/ErYjE9cAXX8qbyxa64bmEEblNc0Fibo903S1coUFKG/H5hpXJOUQzNNIKRlOCXZL7Dg==";


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().toString());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(generateKey())
                .compact();
    }
    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    public String extractUsername(String token){
        return extractClaims(token).getSubject();

    }
    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String username, UserDetails userDetails, String token) {
        //TODO - check if username is same as username in userDetails
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        //TODO - check if token is not expired
        return extractClaims(token).getExpiration().before(new Date());
    }
}
