package com.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * 用户健康数据的实例
 * 主要用于用户健康表
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="用户健康表")
public class UserHealth {
    //主键iD
    //用户id(唯一键open_id)
    private String openId;
    //用户名称
    private String userName;
    //创建时间
    private String createTime;
    //地址
    private String nowAddress;
    //纬度
    private double longitude;
    //经度
    private double latitude;

}
