package com.app.service;

import com.app.bean.*;

import java.util.List;

public interface UserHealthService {
    //新增用户
    void AddUser(String code);

    //打卡用户
    void PunchUser(UserHealth userHealth);

    //用户请假
    void UserLeace(UserLeave userLeave);

    //查询用户
    UserHealth GetUser(String user_name);

    //用户新增药品
    void addDrug(String userDrug);

    //返回药品
    List<UserDrugResult> queryDrug(Integer page, Integer rows, String openId);

    //返回药品总数
    Integer drugNum(String openId);

    //新增食物
    void addFood(String userFood);

    //订阅药品
    DoResult subscription(String openId,String drugUuid,String status);
}
