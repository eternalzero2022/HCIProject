package com.njuse.battlerankbackend.aop;

import com.njuse.battlerankbackend.enums.RoleEnum;
import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.User;
import com.njuse.battlerankbackend.util.SecurityUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class RoleAuthorizationAspect {

    @Autowired
    private SecurityUtil securityUtil;

    @Before("@annotation(roleAuthorization)")
    public void authorization(RoleAuthorization roleAuthorization) {

        RoleEnum[] roles = roleAuthorization.value();

        User user = securityUtil.getCurrentUser();
        if (!Arrays.asList(roles).contains(user.getRole())) {
            throw SelfDefineException.noPermission();
        }
    }
}
