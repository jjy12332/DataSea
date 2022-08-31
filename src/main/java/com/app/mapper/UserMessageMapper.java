package com.app.mapper;

import com.app.bean.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMessageMapper {
    void  addMessage(UserMessage userMessage);
    List<UserMessage> queryMessage(@Param("start")Integer start, @Param("rows") Integer rows);

    Integer MessageNum();
}
