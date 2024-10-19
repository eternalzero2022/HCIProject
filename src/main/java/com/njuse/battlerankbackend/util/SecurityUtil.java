package com.njuse.battlerankbackend.util;

import com.njuse.battlerankbackend.po.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    @Autowired
    HttpServletRequest request;

    public User getCurrentUser() {
        return (User) request.getSession().getAttribute("currentUser");
    }

    public void setCurrentUser(User user) {
        request.getSession().setAttribute("currentUser", user);
    }
}
