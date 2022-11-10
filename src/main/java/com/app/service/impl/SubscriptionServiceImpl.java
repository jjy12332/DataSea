package com.app.service.impl;

import com.app.bean.UserDrug;
import com.app.bean.UserDrugResult;
import com.app.mapper.SubscriptionMapper;
import com.app.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 订阅类
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    //用户订阅mapper
    @Autowired
    private SubscriptionMapper sst;

    //订阅状态表插入数据信息
    public void insertSubscription(UserDrug udr){
        sst.insertSubscription(udr);
    }

    //订阅状态表删除数据信息
    public void deleteSubscription(String openId,String uuid){
        sst.deleteSubscription(openId,uuid);
    }

    //订阅/删除订阅
    public void updateSubscription(String openId,String drugUuid,String status){
        sst.updateSubscription(openId,drugUuid,status);
    }



}
