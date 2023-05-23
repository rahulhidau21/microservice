package com.rahul.auth.security;


import com.rahul.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SignatureException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String createToken(Authentication authentication, boolean rememberMe , User user) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.jwtExpirationInMs);
        } else {
            validity = new Date(now + this.jwtExpirationInMs);
        }

        Map<String , Object> userDetail = new HashMap<>();
        userDetail.put("userId",String.valueOf(user.getId()));
        userDetail.put("userName",user.getUsername());

        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .addClaims(userDetail)
                .claim("auth", authorities)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(validity)
                .compact();
    }
    public boolean validateToken(String authToken) {
        Jws<Claims> claims = null;
        try {
            // Jwt token has not been tampered with
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String authToken) {
        List<SimpleGrantedAuthority> roles = null;
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
        Boolean isAdmin = claims.get("isAdmin", Boolean.class);
        Boolean isUser = claims.get("isUser", Boolean.class);
        if (isAdmin != null && isAdmin == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (isUser != null && isUser == true) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return roles;
    }
}
