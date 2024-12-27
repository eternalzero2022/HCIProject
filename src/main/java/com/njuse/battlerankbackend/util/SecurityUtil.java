package com.njuse.battlerankbackend.util;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SecurityUtil {

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private RedisTemplate redisTemplate;

    public User getCurrentUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw SelfDefineException.notLogin();
        }
        return user;
    }

    public void setCurrentUser(User user) {
        request.getSession().setAttribute("user", user);
    }

}
