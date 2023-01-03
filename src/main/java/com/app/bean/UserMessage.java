package com.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * 用户留言实例
 * 主要用于用户留言表
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="用户留言表")
public class UserMessage {
    // 主键iD
    // VARCHAR(200) '用户唯一ID',
    private String openId;

    // VARCHAR(200) '留言创建时间'
    private String createTime;
    // VARCHAR(250) '用户留言',
    private String content;
    // VARCHAR(250) '用户图片列表',
    private String imageUrl;
    // VARCHAR(100) '用户手机型号',
    private String phoneType;
    //用户头像路径
    private String userAvatar ;
}
