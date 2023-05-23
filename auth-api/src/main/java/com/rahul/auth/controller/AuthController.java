package com.rahul.auth.controller;

import com.rahul.auth.dto.LoginVM;
import com.rahul.auth.dto.ResponseMessage;
import com.rahul.auth.model.User;
import com.rahul.auth.security.JwtTokenUtil;
import com.rahul.auth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/v1/")
public class AuthController {
    @Autowired
    private IUserService userService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private AuthenticationManager authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseMessage> login(@RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.getUsername(),
                loginVM.getPassword()
        );

        User user = userService.getUser(loginVM.getUsername());
        Authentication authentication = authenticationManagerBuilder.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenUtil.createToken(authentication, true, user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, "Bearer " + jwt);
        return new ResponseEntity(new ResponseMessage(HttpStatus.OK.value(), "Logged In Successfully", jwt),
                httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/test")
    public void test(@RequestBody LoginVM loginVM) {
        System.out.print("test");
    }
}
