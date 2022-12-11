package com.app.bean;

import com.app.Enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 *
 * 所有返回结构都由DoResult承接
 * 除了Boolean（部分可能也放到DoResult里）
 *
 */
@Data
@ToString
public class DoResult<T> {

    //状态码
    private Integer status;
    //返回的消息
    private String message;
    //返回的数据
    private T data;

    public DoResult() {
        status = 200;
        message = "请求成功";
    }
    public DoResult(Integer status,String message,T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public DoResult(Integer status,String message){
        this.status = status;
        this.message = message;
    }
    public DoResult(ResultCodeEnum codeEnum,T data){
        this.status = codeEnum.getCode();
        this.message = codeEnum.getMessage();
        this.data = data;
    }

    public boolean isSuccess(){
        return this.status == ResultCodeEnum.SUCCESS.getCode();
    }

    public static <T> DoResult success(){
        return new DoResult(ResultCodeEnum.SUCCESS,null);
    }
    public static <T> DoResult success(T data){
        return new DoResult(ResultCodeEnum.SUCCESS,data);
    }
    public static <T> DoResult success(String message,T data){
        return new DoResult(ResultCodeEnum.SUCCESS.getCode(),message,data);
    }
    public static <T> DoResult success(Integer status,String message,T data){
        return new DoResult(status,message,data);
    }

    public static <T> DoResult<T> error(){
        return new DoResult(ResultCodeEnum.ERROR500,null);
    }
    public static <T> DoResult<T> error(T data){
        return new DoResult(ResultCodeEnum.ERROR500,data);
    }
    public static <T> DoResult<T> error(String message,T data){
        return new DoResult(ResultCodeEnum.ERROR500.getCode(),message,data);
    }
    public static <T> DoResult<T> error(Integer status,String message,T data){
        return new DoResult(status,message,data);
    }


}