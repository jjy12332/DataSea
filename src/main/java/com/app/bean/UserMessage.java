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
@Table(name="用户留言表")
public class UserMessage {
    // 主键iD
    // VARCHAR(200) '用户唯一ID',
    private String openId;
    // VARCHAR(100) '用户名',
    private String userName;
    // VARCHAR(200) '留言创建时间'
    private String createTime;
    // VARCHAR(200) '用户头像',
    private String avatarUrl;
    // VARCHAR(250) '用户留言',
    private String content;
    // VARCHAR(250) '用户图片列表',
    private String imageUrl;
    // VARCHAR(100) '用户手机型号',
    private String phoneType;
}
