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
@Table(name="用户食物表")
public class UserFood {
    //主键iD
    //用户id(唯一键open_id)
    private String openId;
    //用户名称
    private String userName;
    //创建时间
    private String createTime;
    //食物
    private String foodName;
    //每100克能量
    private String foodCalorie;
    //相似度
    private String foodProbability;

}
