package com.njuse.battlerankbackend.exception;

public class SelfDefineException extends RuntimeException{
    public SelfDefineException(String message){super((message));}
    public static SelfDefineException phoneAlreadyExits(){
        return new SelfDefineException("手机号已被注册！");
    }

    public static SelfDefineException loginFaultPhone(){
        return new SelfDefineException("账号电话错误！");
    }

    public static SelfDefineException loginFaultPassword(){
        return new SelfDefineException("密码错误！");
    }
}
