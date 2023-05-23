package com.rahul.auth.service;

import com.rahul.auth.model.User;

public interface IUserService {
    User getUser(String username);
}
