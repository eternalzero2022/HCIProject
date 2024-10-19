package com.njuse.battlerankbackend.exception;

import com.njuse.battlerankbackend.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 这个类能够接住项目中所有抛出的异常，
 * 使用了RestControllerAdvice切面完成，
 * 表示所有异常出现后都会通过这里。
 * 这个类将异常信息封装到ResultVO中进行返回。
 */

/**
 * RestControllerAdvice是一个用于定义全局异常处理器的注解。当应用程序内发生未捕获的异常时，全局异常处理器将捕获该异常并返回对应的响应，以避免应用程序崩溃
 * 它可以处理所有**控制器**中抛出的异常，包括请求处理方法中的异常、控制器构造函数中的异常等。
 * RestControllerAdvice注解是@ControllerAdvice和@ResponseBody注解的组合，
 * 它的作用是将所有的异常处理结果都以**JSON格式**返回给客户端。
 *
 *
 Spring Boot会在@ControllerAdvice或@RestControllerAdvice注解的类中查找与异常匹配的@ExceptionHandler注解标记的方法，并执行该方法来处理异常。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    //用于定义异常处理方法
    @ExceptionHandler(value = SelfDefineException.class)//@ExceptionHandler注解中可以添加参数，参数是某个异常类的class，代表这个方法专门处理该类异常，比如这样：
    public ResultVO<String> handleAIExternalException(Exception e) {
        e.printStackTrace();
        return ResultVO.buildFailure(e.getMessage());//处理方法可以返回任何类型的值，如果返回对象是DataVO类型，则会将其转换为JSON格式并返回给客户端。
    }
}
