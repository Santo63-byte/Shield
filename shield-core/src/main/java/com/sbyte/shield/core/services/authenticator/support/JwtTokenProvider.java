package com.sbyte.shield.core.services.authenticator.support;

import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.datasource.storage.TokenStorage;
import com.sbyte.shield.dto.BklTokenDTO;
import com.sbyte.shield.dto.CredentialsDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
@Component("jwtTokenProvider")
public class JwtTokenProvider {

    @Value("${shield.jwt.secret}")
    private String jwtSecret;

    @Value("${shield.jwt.access-token-ttl-seconds:600}")
    private long accessTokenTtlSeconds;

    @Value("${shield.jwt.refresh-token-ttl-seconds:86400}")
    private long refreshTokenTtlSeconds;

    private Key signingKey;

    @Autowired
    HttpServletRequest request;

    @Autowired
    TokenStorage tokenStorage;

    @PostConstruct
    public void init() {
        validateAndInitializeSigningKey();
    }

    private void validateAndInitializeSigningKey() {
        if (jwtSecret == null || jwtSecret.trim().isEmpty()) {
            throw new IllegalStateException(
                    "JWT secret key is not configured. Please set 'shield.jwt.secret' property."
            );
        }

        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);

        // checking for minimum key length of 256 bits (32 bytes)
        if (keyBytes.length < 32) {
            throw new IllegalStateException(
                    String.format(
                            "JWT secret key must be at least 256 bits (32 characters). Current length: %d",
                            keyBytes.length
                    )
            );
        }
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        log.info("JWT signing key initialized successfully with length: {} bits", keyBytes.length * 8);
    }
    public String generateToken(CredentialsDTO credentials) {
        Instant now = Instant.now();
        String username = credentials.getUserName();
        Instant expiry = now.plusSeconds(accessTokenTtlSeconds);

        return Jwts.builder()
                .setSubject(username)
                .claim("sessionId",credentials.getSsnid())
                .claim("deviceId",credentials.getDeviceId())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public long getExpiresInSeconds() {
        return accessTokenTtlSeconds;
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Shield_Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public String generateRefreshToken(String username) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(refreshTokenTtlSeconds);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
    public Authentication getAuthenticationFromContext(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public void clearAuthenticationContext(){
        SecurityContextHolder.clearContext();
    }

    public boolean isValidToken(String token) {
        if(token == null || token.trim().isEmpty()){
            log.warn("JWT token is null or empty");
            return false;
        }
        /// check if token in blacklist
        List<BklTokenDTO> blackListedTokens = tokenStorage.getBlackListedTokensByUserId(getUsernameFromToken(token));
        if(blackListedTokens != null && !blackListedTokens.isEmpty()) {
            for(BklTokenDTO bklToken : blackListedTokens) {
                if(bklToken.getAccessToken().equals(token)) {
                    log.warn("JWT token is blacklisted");
                    return false;
                }
            }
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            log.warn("JWT token is expired: {}", ex.getMessage());
            return false;
        } catch (io.jsonwebtoken.MalformedJwtException ex) {
            log.warn("JWT token is malformed: {}", ex.getMessage());
            return false;
        } catch (io.jsonwebtoken.SignatureException ex) {
            log.warn("JWT signature validation failed: {}", ex.getMessage());
            return false;
        } catch (io.jsonwebtoken.UnsupportedJwtException ex) {
            log.warn("JWT token is unsupported: {}", ex.getMessage());
            return false;
        } catch (IllegalArgumentException ex) {
            log.warn("JWT claims string is empty: {}", ex.getMessage());
            return false;
        }
    }

    public String getSessionIdFromRequest() {
        String token = extractTokenFromRequest(request);
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("sessionId", String.class);
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException ex) {
            log.error("Failed to extract sessionId from JWT token: {}", ex.getMessage());
            throw new ShieldExceptions("Invalid token", "Client Session is not valid", 400);
        }
    }
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException ex) {
            log.error("Failed to extract username from JWT token: {}", ex.getMessage());
            throw new IllegalArgumentException("Invalid JWT token", ex);
        }
    }

}
