package com.app.mapper;

import com.app.bean.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMessageMapper {
    //添加留言
    void  addMessage(UserMessage userMessage);

    //返回留言
    List<UserMessage> queryMessage(@Param("start")Integer start, @Param("rows") Integer rows);

    //返回留言总数
    Integer MessageNum();
}
