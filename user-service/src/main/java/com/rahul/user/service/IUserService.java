package com.rahul.user.service;

import com.rahul.user.model.User;

public interface IUserService {
    User getUser();

    User getUser(Long id);
}
