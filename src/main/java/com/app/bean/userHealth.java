package com.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class userHealth {
    //用户名称
    private String UserName;
    //创建时间
    private Date CreateTime;
    //地址
    private String NowAddress;
    //用户id(唯一键open_id)
    private String OpenId;
}
