package com.app.Enums;

import lombok.*;

/**
 *
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"成功"),
    ERROR500(500,"系统内部错误");

    public Integer code;
    public String message;

//    public void setCode(){
//        this.code = code;
//    }
//
//    public void setMessage(){
//        this.message = message;
//    }

    private ResultCodeEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
