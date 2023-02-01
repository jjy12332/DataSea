package com.app.controller;

import com.app.bean.DoResult;
import com.app.bean.QueryPage;
import com.app.bean.UserMessage;
import com.app.config.FtpUtil;
import com.app.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class UserMessageController {

    //地址IP
    @Value("${FTP.ADDRESS}")
    private String host;
    // 端口
    @Value("${FTP.PORT}")
    private int port;
    // ftp用户名
    @Value("${FTP.USERNAME}")
    private String userName;
    // ftp用户密码
    @Value("${FTP.PASSWORD}")
    private String passWord;
    // 文件在服务器端保存的主目录
    @Value("${FTP.BASE-PATH}")
    private String basePath;
    // 访问图片时的基础url
    @Value("${FTP.URL}")
    private String baseUrl;


    @Autowired
    UserMessageService emp;

    //查询留言，分页查询【RequestParam，PathVariable RequestBody】
    @RequestMapping(value = "/emp/queryMessage",method = RequestMethod.POST)
    public DoResult message(QueryPage queryPage){

        return emp.queryMessage(queryPage);
    }

    //增加留言
    @RequestMapping(value = "/emp/addmessage",method = RequestMethod.POST)
    public DoResult addmessage(UserMessage userMessage){
        return emp.addMessage(userMessage);
    }

    //查询留言总条数
    @RequestMapping(value = "/emp/MessageNum",method = RequestMethod.POST)
    public DoResult MessageNum(){
        return emp.MessageNum();
    }

    //上传图片
    @RequestMapping(value = "/emp/uploadImage",method = RequestMethod.POST)
    public void uploadImage(@RequestParam("file") MultipartFile uploadFile) throws Exception{
        try{
            //1、给上传的图片生成新的文件名
            //1.1获取原始文件名
            String oldName = uploadFile.getOriginalFilename();
            //UUID+文件名后缀
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
            SimpleDateFormat DateFormat = new SimpleDateFormat("/yyyy/MM/dd");
            String filePath = DateFormat.format(new Date());
            System.out.println("filePath:"+filePath);

            //3、把图片上传到图片服务器
            //3.1获取上传的io流
            InputStream input = uploadFile.getInputStream();

            //3.2调用FtpUtil工具类进行上传
            boolean result = FtpUtil.uploadFile(host, port, userName, passWord, basePath, filePath, newName, input);
            if(result){
                System.out.println("传输完毕");
            }else {
                System.out.println("传输有问题");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

//        emp.uploadImage(file);
    }
}
