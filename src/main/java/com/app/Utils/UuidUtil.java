package com.app.Utils;

import java.util.UUID;

public class UuidUtil {
    //获取uuid，用于不同方法中
    public static String uuid(){

        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 订阅id
     * id前六位 和 时间值时分秒 组成唯一订阅id
     */
    public static String subUuid(String str,String time){
        String subid = str.substring(0,4) + time.replaceAll(":","");
        System.out.println(subid);
        return subid;
    }
}
