package com.tomek.cardatabase.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    static final long EXPIRATIONTIME = 86400000L * 1000; // 1000 day in ms
    static final String PREFIX = "Bearer";
    // Generate secret key. Only for the demonstration
    // You should read it from the application configuration
    static final Key key =   Keys.secretKeyFor
            (SignatureAlgorithm.HS256);
    // Generate signed JWT token
    public String getToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    // Get a token from request Authorization header,
    // verify a token and get username
    public String getAuthUser(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader
                (HttpHeaders.AUTHORIZATION);
        System.out.println(token + " to jest token");
        if (token != null) {
            String user = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null)
                return user;
        }
        return null;
    }
    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token.replace(PREFIX, ""))
                .getBody();
        return claims.getSubject();
    }
    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }
}
