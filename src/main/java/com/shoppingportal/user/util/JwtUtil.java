package com.shoppingportal.user.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shoppingportal.user.exception.JwtTokenException;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    public Claims getClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String generateToken(String id) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + tokenValidity;
        Date exp = new Date(expMillis);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public void validateToken(final String token) throws JwtTokenException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (MalformedJwtException ex) {
            throw new JwtTokenException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenException("JWT claims string is empty.");
        }
    }

}

