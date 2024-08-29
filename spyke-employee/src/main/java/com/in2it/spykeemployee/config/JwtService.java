package com.in2it.spykeemployee.config;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;


@Service
public class JwtService {

    private static final String SECRET = "865386532653286532865323fcbsnnh863bccferfywertbcrt37r34"; // Consider using a more secure way to manage this key

    // Generate JWT Token
    public String generateToken(UserDetails user) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            throw new IllegalArgumentException("User has no authorities.");
        }

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", populateAuthorities(authorities))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Get signing key
    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Convert authorities to a string representation
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null) {
            return "";
        }
        Set<String> authoritiesSet = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return String.join(",", authoritiesSet);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token has expired", e);
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a claim using a function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Check if token is valid
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final String authorities = extractClaim(token, claims -> claims.get("authorities", String.class));
        return username.equals(userDetails.getUsername()) &&
               validateAuthorities(authorities, userDetails.getAuthorities());
    }

    // Validate authorities from the token
    private boolean validateAuthorities(String tokenAuthorities, Collection<? extends GrantedAuthority> userAuthorities) {
        Set<String> tokenAuthoritiesSet = Arrays.stream(tokenAuthorities.split(","))
                .collect(Collectors.toSet());
        Set<String> userAuthoritiesSet = userAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return tokenAuthoritiesSet.equals(userAuthoritiesSet);
    }
}