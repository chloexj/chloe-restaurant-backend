package com.chloe.service;

import com.chloe.dto.UserLoginDTO;
import com.chloe.entity.User;

public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}
