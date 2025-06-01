package ua.rdev.nure.mppzbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import ua.rdev.nure.mppzbackend.entities.User;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import static ua.rdev.nure.mppzbackend.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static ua.rdev.nure.mppzbackend.Constants.SIGNING_KEY;

@Component
public class JwtTokenUtil implements Serializable {

    public String getEmail(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getEmail());
    }

    private String doGenerateToken(String subject) {
        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(subject))
                .setIssuer("mppz")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, User userDetails) {
        final String username = getEmail(token);
        return username.equals(userDetails.getEmail()) && !isTokenExpired(token);
    }
}
