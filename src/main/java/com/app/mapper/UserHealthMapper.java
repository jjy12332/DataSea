package com.app.mapper;



import com.app.bean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//如果不加下面注解会报错，找不到此文件
@Mapper
@Repository
public interface UserHealthMapper {
     //查询用户
     UserInfo selectUserInfo(String openId);

     //新增用户
     void insertUserInfo(UserInfo userInfo);

     //用户打卡信息
     void insertUserHealth(UserHealth userHealth);

     //用户请假
     void insertData2(List<UserLeave> list);

     //增加药品总表
     void insertDrug(UserDrug userDrug);

     //删除药品
     void deleteDrug(@Param("openId") String openId,@Param("drugUuid")String drugUuid);

     //药品粒度删除药品表
     void deleteDrugDetail(@Param("openId") String openId,@Param("drugUuid")String drugUuid);

     //修改天数
     void updateDrugDays(@Param("openId") String openId,@Param("drugUuid")String drugUuid,@Param("eatDays")String days,@Param("endTime")String endTime);

     //增加药品明细表
     void insertDrugDetail(UserDrugDetail userDrugDetail);

     //删除药品详细信息，按照时间粒度
     void deleteDrugDetailDate(@Param("openId") String openId,@Param("drugUuid")String drugUuid,@Param("subId") String subId);

     //更新订阅状态
     void updateSubscription(@Param("openId") String openId, @Param("drugUuid") String drugUuid, @Param("subId") String subId,@Param("status") String status);


     //增加菜品
     void insertFood(UserFood userFood);

     //返回药品
     List<UserDrug> queryDrug(@Param("start")Integer start, @Param("rows") Integer rows,@Param("openId") String openId);

     //返回药品总数
     Integer drugNum(String openId);

     //查询药品详细信息
     List<UserDrugDetail> queryDrugDetail(@Param("openId") String openId, @Param("drugUuid") String drugUuid);

     List<String> queryDrugDetailSubId(@Param("openId") String openId, @Param("drugUuid") String drugUuid);

     //订阅药品，查询药品详细信息
     UserDrugDetail subscriptionDrug(@Param("openId") String openId, @Param("drugUuid") String drugUuid,@Param("subId") String subId);

     //根据用户id和药品id查询指定药品名字
     String queryDrugName(@Param("openId") String openId, @Param("drugUuid") String drugUuid);

     //获取用户权限

}
