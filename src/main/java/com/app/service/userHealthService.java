package com.app.service;

import com.alibaba.fastjson.JSON;
import com.app.bean.userHealth;
import com.app.mapper.userHealthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class userHealthService {

    @Autowired
    private userHealthMapper emp;

    // 新增用户
    public void AddUser(String str){
        //将前台传过来的字符串转为Json
        userHealth a = JSON.parseObject(str, userHealth.class);
        emp.InsertData(a);
    }

    // 打卡用户
    public void PunchUser(String str) {
        /**
         *这边会抛出空指针异常
         * 目测是连接数据库的问题
         * */
        try{
            //将前台传过来的字符串转为Json
            userHealth a = JSON.parseObject(str, userHealth.class);
            emp.InsertData(a);
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    // 查询用户
    @GetMapping("/emp/{user_name}")
    @ResponseBody
    public userHealth GetUser(String user_name){
        return emp.GetName(user_name);
    }

}
