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

    public static SelfDefineException creatCollectionFault1(){
        return new SelfDefineException("您已经创建过同一个名字的帖子了哦~");
    }

    public static SelfDefineException getCollectionFault(){
        return new SelfDefineException("您要找的合集找不到了呢~");
    }
}
