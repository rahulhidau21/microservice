package com.rahul.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @PostMapping
    public void save() {
        log.info("reqeust is recivied");
    }

    @GetMapping
    public void get() {
        log.info("reqeust is recivied");
    }
}
