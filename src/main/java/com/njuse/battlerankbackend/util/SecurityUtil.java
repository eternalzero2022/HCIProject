package com.njuse.battlerankbackend.util;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.User;
import com.njuse.battlerankbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    @Autowired
    HttpServletRequest request;

    public User getCurrentUser() {
        User user = (User) request.getSession().getAttribute("currentUser");
        if (user == null) {
            throw SelfDefineException.notLogin();
        }
        return user;
    }

    public void setCurrentUser(User user) {
        request.getSession().setAttribute("currentUser", user);
    }
}
