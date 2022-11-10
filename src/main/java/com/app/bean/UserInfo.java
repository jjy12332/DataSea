package com.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="用户信息表")
public class UserInfo {
    //主键iD
    //用户id(唯一键open_id)
    private String openId;
    //用户名称
    private String userName;
    //用户头像路径
    private String userAvatar ;
    //创建时间
    private String createTime ;
    //手机号
    private String phone;
    //token值（自己的token，目前先为空）
    private String token;
}