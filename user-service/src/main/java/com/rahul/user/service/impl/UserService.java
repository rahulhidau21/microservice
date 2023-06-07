package com.rahul.user.service.impl;

import com.rahul.commons.exception.GenricException;
import com.rahul.user.model.User;
import com.rahul.user.repository.UserRepository;
import com.rahul.user.service.IUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class UserService implements IUserService {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Long userId = Long.valueOf((String) claims.get("userId"));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "Spring's RestTemplate");  // value can be whatever
        headers.add("Authorization", "Bearer " + token);

        ResponseEntity<User> user = restTemplate.exchange("http://localhost:8080/user-service/api/v1/user/{id}",
                HttpMethod.GET, new HttpEntity<>(headers), User.class, userId);
        return user.getBody();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new GenricException("User not Found", HttpStatus.NOT_FOUND));
    }
}
