package com.app.service;

import com.app.bean.UserDrug;
import com.app.bean.UserHealth;
import com.app.bean.UserLeave;

import java.util.List;

public interface UserHealthService {
    //新增用户
    void AddUser(String str);

    //打卡用户
    void PunchUser(UserHealth userHealth);

    //用户请假
    void UserLeace(UserLeave userLeave);

    //查询用户
    UserHealth GetUser(String user_name);

    //用户新增药品
    void addDrug(String userDrug);

    //返回药品
    List<UserDrug> queryDrug(Integer page, Integer rows, String openId);

    //返回药品总数
    Integer drugNum(String openId);

    //新增食物
    void addFood(String userFood);
}
