package com.app.service;

import com.app.bean.UserDrug;

public interface SubscriptionService {

    //订阅状态表插入数据信息
    void insertSubscription(UserDrug udr);

    //订阅状态表删除数据信息
    void deleteSubscription(String openId,String uuid);

    //订阅/删除订阅
    void updateSubscription(String openId,String drugUuid,String status);
}
