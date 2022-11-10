package com.app.mapper;

import com.app.bean.UserDrug;
import com.app.bean.UserDrugResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 订阅表mapper
 */
@Mapper
@Repository
public interface SubscriptionMapper {
    //订阅状态表插入数据信息
    void insertSubscription(UserDrug udr);

    //订阅状态表删除数据信息
    void deleteSubscription(String openId,String uuid);

    //订阅/删除订阅（根据用户id和药品id修改信息）
    void updateSubscription(@Param("openId") String openId, @Param("drugUuid") String drugUuid,@Param("status") String status);



}
