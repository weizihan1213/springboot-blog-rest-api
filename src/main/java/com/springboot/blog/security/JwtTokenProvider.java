package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationDateInMilliseconds;

    // Generate JWT Token
    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Date currentDate = new Date();

        // The current milliseconds plus the expiration milliseconds
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDateInMilliseconds);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(key())
                .compact();

        return token;
    }

    // Get Username from JWT token
    public String getUserName(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (MalformedJwtException malformedJwtException) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is null or empty");
        } catch (SignatureException signatureException) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
