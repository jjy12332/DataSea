package com.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * 用户药品表
 * 主要用于用户药品表
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="用户药品表")
public class UserDrug {
    //主键iD
    //用户ID
    private String openId;
    //创建时间
    private String createTime;
    //药品id
    private String drugUuid;

    //药品名字
    private String drugName;

    //吃药的天数
    private String eatDays;
    //结束时间
    private String endTime;
    //药品单价
    private int drugPrice;
    //药品厂商
    private String manufacturer;

}
