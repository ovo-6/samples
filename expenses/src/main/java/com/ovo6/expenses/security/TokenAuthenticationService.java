package com.ovo6.expenses.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Service for dealing with JWT tokens.
 */
public class TokenAuthenticationService {

    private long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 10; // 10 days
    private String secret = "ThisIsASecret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username, String role) {
        // We generate a token now.
        String JWT = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);


        try {
            response.getWriter().print("{\"role\": \"" + role + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.

            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            String username = body.getSubject();
            if (username != null) // we managed to retrieve a user
            {
                return new AuthenticatedUser(username, new SimpleGrantedAuthority((String) body.get("role")));
            }
        }
        return null;
    }
}
