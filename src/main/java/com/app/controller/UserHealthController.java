package com.app.controller;


import com.alibaba.fastjson.JSONObject;
import com.app.bean.*;
import com.app.mapper.SubscriptionMapper;
import com.app.service.SubscriptionService;
import com.app.service.UserHealthService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 查东西都用get
 * 其余都用post（涉及到改系统得情况）
 */
@RestController
public class UserHealthController {

    @Autowired
    UserHealthService emp;


    // 新增用户
    @RequestMapping(value = "/emp/adduser")
    public void AddUser(String code) {
        System.out.printf(code);
        emp.AddUser(code);
    }

    // 打卡用户
    @RequestMapping(value = "/emp/clock", method = RequestMethod.POST)
    public void PunchUser(UserHealth userHealth) {
        System.out.println(userHealth.toString());
        emp.PunchUser(userHealth);
    }

    //用户请假
    @RequestMapping(value = "/emp/leave", method = RequestMethod.POST)
    public void UserLeace(UserLeave userLeave) {
        emp.UserLeace(userLeave);
    }

    // 查询用户
    @GetMapping("/emp/{user_name}")
    @ResponseBody
    public UserHealth GetUser(@PathVariable("user_name") String user_name) {
        return emp.GetUser(user_name);
    }

    //用户新增药品
    @RequestMapping(value = "/emp/addDrug", method = RequestMethod.POST)
    public void AddDrug(@RequestBody String userDrug) throws JSONException {
        emp.addDrug(userDrug);
    }

    //用户新增菜品
    @RequestMapping(value = "/emp/addMeal", method = RequestMethod.POST)
    public void AddMeal(@RequestBody String userfood) {
        System.out.println(userfood);
        emp.addFood(userfood);
    }

    //查询药品，分页查询【RequestParam，PathVariable，RequestBody（post消息体的）】
    @RequestMapping(value = "/emp/queryDrug", method = RequestMethod.POST)
    public List<UserDrugResult> queryDrug(QueryPage queryPage) {
        System.out.println(queryPage.getPage() + queryPage.getRows() + queryPage.getOpenId());
        List<UserDrugResult> list = emp.queryDrug(queryPage.getPage(), queryPage.getRows(), queryPage.getOpenId());
        return list;
    }

    //查询药品总条数
    @RequestMapping(value = "/emp/drugNum", method = RequestMethod.POST)
    public Integer drugNum(String openId) {
        return emp.drugNum(openId);
    }

    //用户订阅状态接口
    @RequestMapping(value = "/emp/subscription", method = RequestMethod.POST)
    public DoResult subscription(String openId,String drugUuid,String status) {
        DoResult doResult = new DoResult();
        emp.subscription(openId, drugUuid, status);

        return doResult;
    }




}
