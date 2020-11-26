package dev.jooz.Web.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    public final static long TOKEN_VALIDATION_SECOND=1000L*60*30;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND=1000L*60*60*24*30;

    public JwtUtil(){}

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey(String secretKey){
        byte[] keyBytes=secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException{
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token){
        return extractAllClaims(token).get("username",String.class);
    }

    public Boolean isTokenExpired(String token){
        final Date expiration=extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(String username){
        return doGenerateToken(username,TOKEN_VALIDATION_SECOND);
    }
    public String generateRefreshToken(String username){
        return doGenerateToken(username,REFRESH_TOKEN_VALIDATION_SECOND);
    }
    public String doGenerateToken(String username,long expireTime){
        Claims claims=Jwts.claims();
        claims.put("username",username);

        String jwt=Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }
    public Boolean validateToken(String token, String username){
        final String tokenUsername=getUsername(token);

        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}
