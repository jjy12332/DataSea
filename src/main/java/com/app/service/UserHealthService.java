package com.app.service;

import com.app.bean.*;

import java.util.List;

public interface UserHealthService {
    //新增用户
    DoResult AddUser(String jwt,String code, String userName,String userAvatar) throws Exception;

    //打卡用户
    void PunchUser(UserHealth userHealth);

    //用户请假
    void UserLeace(UserLeave userLeave);

    //查询用户
//    UserHealth GetUser(String user_name);

    //用户新增药品
    void addDrug(String userDrug);

    //返回药品
    List<UserDrug> queryDrug(QueryPage queryPage);

    //返回药品总数
    Integer drugNum();

    //新增食物
    void addFood(String userFood);

    //订阅药品
    DoResult subscription(String drugUuid,String status);
}
