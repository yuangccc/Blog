package com.yuangcc.service;

import com.yuangcc.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
