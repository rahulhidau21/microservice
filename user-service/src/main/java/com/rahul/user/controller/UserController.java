package com.rahul.user.controller;

import com.rahul.commons.dto.ResponseMessage;
import com.rahul.user.model.User;
import com.rahul.user.service.INotificationService;
import com.rahul.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IUserService userService;

    @PostMapping
    public void save() {
        log.info("reqeust is recivied");
    }

    @GetMapping
    public ResponseEntity<ResponseMessage> get() {
        return new ResponseEntity(new ResponseMessage(HttpStatus.OK.value(), "", userService.getUser()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/send")
    public void send() {
        notificationService.send();
    }
}
