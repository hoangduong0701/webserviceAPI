package com.dripg.drip_shop.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenHelper {
    @Value("${jwt.auth.secret_key}")
    private String secretKey;
    @Value("${jwt.auth.app}")
    private String appName;
    @Value("${jwt.auth.expires_in}")
    private Long expiresIn;



    public String generateToken(String userName) {
        return Jwts.builder()
                .issuer(appName)
                .subject(userName)
                .setIssuedAt(new Date())
                .expiration(generateExpirationDate())
                .signWith(getSiginKey())
                .compact();
    }

    private Key getSiginKey() {
        byte[] keysBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keysBytes);
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn * 1000L);
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (
                username != null && username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                );
    }
    private boolean isTokenExpired(String token) {
        Date expireData = getExperationDate(token);
        return expireData.before(new Date());
    }

    private Date getExperationDate(String token) {
        Date experationDate;
        try {
            Claims claims = this.getAllClaimsFromToken(token);
            experationDate = claims.getExpiration();
        } catch (Exception e) {
            experationDate = null;
        }
        return experationDate;
    }

    private String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
    public String getUserNameFromToken(String authToken) {
        String userName;
        try{
            final Claims claims = this.getAllClaimsFromToken(authToken);
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }

        return userName;
    }
    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(getSiginKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
