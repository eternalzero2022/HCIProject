package com.njuse.battlerankbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//使用了泛型类
public class ResultVO<T> implements Serializable {

    private String code;

    private String msg;

    private T result;
    //使用了泛型方法，同时使用泛型方法和泛型类可以实现即可定义不同类型的类；
    // 还可以在该对象的方法中使用其他类型的参数，不受定义对象时的类型影响

    //静态方法不可以使用类上定义的泛型，所以须将泛型定义在方法上

    //泛型定义在方法上
    public static <T> ResultVO<T> buildSuccess(T result) {
        return new ResultVO<T>("200", null, result);
    }

    public static <T> ResultVO<T> buildFailure(String msg) {
        return new ResultVO<T>("400", msg, null);
    }
}