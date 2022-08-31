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
@Table(name="用户药品表")
public class UserDrug {
    //主键iD
    //用户ID
    private String openId;
    //用户名
    private String userName;
    //创建时间
    private String createTime;
    //什么时间点吃药 早上、中午、晚上
    private String eatTime;
    //药品名字
    private String drugName;
    //早上饭前/饭后
    private String eatMorning;
    //中午饭前/饭后
    private String eatNoon;
    //晚上饭前/饭后
    private String eatNight;
    //吃药的天数
    private String eatDays;
    //早上吃药数量
    private String eatMorningNum;
    //中午吃药数量
    private String eatNoonNum;
    //晚上吃药数量
    private String eatNightNum;
    //早上吃药时间
    private String timeMorning;
    //中午吃药时间
    private String timeNoon;
    //晚上吃药时间
    private String timeNight;
    //是否重要
    private int drugPrice;

}
