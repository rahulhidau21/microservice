package com.rahul.auth.security;

import com.rahul.auth.model.User;
import com.rahul.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .map(user -> createSpringSecurityUser(s, user))
                .orElseThrow(() -> new RuntimeException("Invalid Username Or Password"));
    }

    private UserDetails createSpringSecurityUser(String lowercaseLogin, User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUsername()));
        return new org.springframework.security.core.userdetails.User(lowercaseLogin, user.getPassword(), grantedAuthorities);
    }
}
