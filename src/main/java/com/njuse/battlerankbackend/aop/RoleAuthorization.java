package com.njuse.battlerankbackend.aop;

import com.njuse.battlerankbackend.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAuthorization {

    // By default, access is allowed to all
    // If a method is only accessible to administrators,
    // use the annotation `@RoleAuthorization(RoleEnum.ADMIN)`
    RoleEnum[] value() default {
            RoleEnum.ADMIN, RoleEnum.USER
    };
}
