package com.app.Util;

import java.util.UUID;

public class UuidUtil {
    //获取uuid，用于不同方法中
    public static String uuid(){

        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
