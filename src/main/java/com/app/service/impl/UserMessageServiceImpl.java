package com.app.service.impl;

import com.app.bean.DoResult;
import com.app.bean.QueryPage;
import com.app.bean.UserMessage;
import com.app.bean.UserToken;
import com.app.mapper.UserMessageMapper;
import com.app.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    UserMessageMapper emp;

    //定义全局变量
    String realPath = "/";
    //用于解决图片文件存储问题
    List a = new ArrayList<Byte[]>();
    String str = "";

    //查询用户留言总数
    public DoResult MessageNum(){
        int num = emp.MessageNum();
        return DoResult.success(num);
    }

    //查询用户留言,分页查询
    public DoResult queryMessage(QueryPage queryPage){
        //从请求头中获取openId
        String openId = ((UserToken) RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();
        queryPage.setOpenId(openId);

        Integer start=(queryPage.getPage()-1)*queryPage.getRows();
        List<UserMessage> pList= emp.queryMessage(start,queryPage.getRows());
        return DoResult.success(pList);
    }

    //增加用户留言
    public DoResult addMessage(UserMessage userMessage){

        //从请求头中获取openId
        String openId = ((UserToken) RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();
        userMessage.setOpenId(openId);
        try{
            emp.addMessage(userMessage);
        }catch (Exception e){
            DoResult.error("留言插入失败","");
        }
        return DoResult.success("留言插入成功","");
    }

    //增加评论
    /*System.out.println("业务getBytes:"+filePath.getBytes());//获取文件字节码
    System.out.println("业务getSize:"+filePath.getSize());//获取文件大小
    System.out.println("业务getContentType:"+filePath.getContentType());//获取文件类型jpeg就是jpg / png
    System.out.println("业务getName:"+filePath.getName());//获取前端name的值
    System.out.println("业务getOriginalFilename:"+filePath.getOriginalFilename());//获取文件名（并非图像一开始自带名字）*/
    //上传图片【只上传图片的字节码】！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
    public String uploadImage(MultipartFile filePath) throws IOException {
        String path = "http:\\app\\";
        File file = new File(path);


        a.add(filePath.getBytes());
//        System.out.println(a.toArray().toString());
        for(int i=0;i<a.toArray().length;i++){
            str += "\""+a.get(i)+"\"";
            if(i+1 != a.toArray().length){
                str+= ",";
            }
        }
//        System.out.println(str);
        return str;
    }

    //上传图片【涉及到将本地图片保存到服务器】
    public void uploadImage1(MultipartFile filePath){
        File file = new File(realPath);
    }
}
