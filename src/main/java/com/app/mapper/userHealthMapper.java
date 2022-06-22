package com.app.mapper;


import com.app.bean.userHealth;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

//如果不加下面注解会报错，找不到此文件
@Mapper
@Repository
public interface userHealthMapper {
     userHealth GetName(String user_name);
     void InsertData(userHealth e);
}
