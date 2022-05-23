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
    //是否请假(1是，0否),数据库这个字段默认给0，如有写入自动改为1，且用户请假则会一次性写入数据库请假天数的条数数据，此数据会变为1
    //private String LeaveFlag;
    //用户id(唯一键open_id)
    private String OpenId;
}
