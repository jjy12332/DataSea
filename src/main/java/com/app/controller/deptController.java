package com.app.controller;


import com.alibaba.fastjson.JSON;
import com.app.bean.userHealth;
import com.app.mapper.userHealthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@RestController
public class deptController {



    //@Autowired
    userHealthMapper emp;

    @GetMapping("/emp/{user_name}")
    public userHealth GetUser(@PathVariable("user_name") String user_name){
        return emp.GetName(user_name);
    }


    //综合注解
    @RequestMapping(value = "/emp/add1",method = RequestMethod.POST)
    @ResponseBody
    public void SetData(HttpServletRequest request, HttpServletResponse response) {
        String str = request.getParameter("json_params");
        System.out.println(str);
        //将前台传过来的字符串转为Json
        userHealth a = JSON.parseObject(str, userHealth.class);
        emp.InsertData(a);
    }



}
