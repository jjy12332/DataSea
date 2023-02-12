package com.app.service;

import com.app.bean.*;

import java.util.List;

public interface UserHealthService {
    //新增用户
    DoResult AddUser(String jwt,String code, String userName,String userAvatar) throws Exception;

    //打卡用户
    DoResult PunchUser(UserHealth userHealth);

    //用户请假
    DoResult UserLeace(UserLeave userLeave);

    //查询用户
//    UserHealth GetUser(String user_name);

    //用户新增药品
    DoResult addDrug(UserDrug userDrug);

    //用户删除药品
    DoResult deleteDrug(String drugUuid);

    //修改药品天数
    DoResult updateDrugDays(String drugUuid,String eatDays,String createTime);

    //用户新增药品详细信息
    DoResult addDrugDetail(UserDrugDetail userDrugDetail);

    //删除药品详细信息
    DoResult deleteDrugDetailDate(String drugUuid,String subId);

    //返回药品
    DoResult queryDrug(QueryPage queryPage);

    //返回药品总数
    DoResult drugNum();

    //查询药品详细信息
    DoResult queryDrugDetail(String drugUuid);

    //新增食物
    DoResult addFood(String userFood);

    //订阅药品
    DoResult subscription(String drugUuid,String subId,String status);

    void subscriptionDrug(String openId,String drugName,String eatTime,String eatNum,String detail);

    //查看文件
    DoResult lookFile();
}
