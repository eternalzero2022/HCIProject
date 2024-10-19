package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.vo.UserVO;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    Boolean register(UserVO userVO);

    Boolean login(UserVO userVO);

    UserVO getUser(HttpSession httpSession);
}
