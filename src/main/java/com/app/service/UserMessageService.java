package com.app.service;

import com.app.bean.DoResult;
import com.app.bean.QueryPage;
import com.app.bean.UserMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserMessageService {
    //查询用户留言总数
    DoResult MessageNum();

    //查询用户留言,分页查询
    DoResult queryMessage(QueryPage queryPage);

    //增加用户留言
    DoResult addMessage(UserMessage userMessage);

    //增加评论
    String uploadImage(MultipartFile filePath) throws IOException;

    //上传图片【涉及到将本地图片保存到服务器】
    void uploadImage1(MultipartFile filePath);
}
