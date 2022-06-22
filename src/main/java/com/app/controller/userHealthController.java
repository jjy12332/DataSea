package com.app.controller;


import com.app.bean.userHealth;
import com.app.service.userHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class userHealthController {

    @Autowired
    userHealthService emp;

    // 新增用户
    @RequestMapping(value = "/emp/adduser")
    public void AddUser(HttpServletRequest request){
        String str = request.getParameter("json_params");
        System.out.printf(str);
        emp.AddUser(str);
    }

    // 打卡用户
    @RequestMapping(value = "/emp/clock",method = RequestMethod.POST)
    public void PunchUser(HttpServletRequest request) {
        String str = request.getParameter("json_params");
        System.out.println(str);
        emp.PunchUser(str);
    }

    // 查询用户
    public userHealth GetUser(@PathVariable("user_name") String user_name){
        return emp.GetUser(user_name);

    }


}
