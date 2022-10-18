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
     UserHealth GetName(String user_name);

     //新增用户，打卡用户
     void insertData1(UserHealth userHealth);

     //用户请假
     void insertData2(List<UserLeave> list);

     //增加药品
     void insertDrug(UserDrug userDrug);

     //增加菜品
     void insertFood(UserFood userFood);

     //返回药品
     List<UserDrug> queryDrug(@Param("start")Integer start, @Param("rows") Integer rows,@Param("openId") String openId);

     //返回药品总数
     Integer drugNum(String openId);
}
