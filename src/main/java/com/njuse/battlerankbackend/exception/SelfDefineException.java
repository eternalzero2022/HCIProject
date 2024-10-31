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

    public static SelfDefineException invalidSessionId() {
        return new SelfDefineException("系统错误：非法的sessionId! ");
    }

    public static SelfDefineException invalidRoundId() {
        return new SelfDefineException("系统错误：非法的roundId! ");
    }

    public static SelfDefineException invalidWinnerId() {
        return new SelfDefineException("系统错误：非法的winnerId! ");
    }

    public static SelfDefineException invalidItemId() {
        return new SelfDefineException("系统错误：非法的itemId! ");
    }

    public static SelfDefineException fileUploadFail() {
        return new SelfDefineException("文件上传失败");
    }

    public static SelfDefineException notLogin() {
        return new SelfDefineException("用户未登陆");
    }

    public static SelfDefineException userNotFound() {
        return new SelfDefineException("用户不存在");
    }
}
