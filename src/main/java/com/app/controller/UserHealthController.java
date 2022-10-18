package com.app.controller;



import com.app.bean.*;
import com.app.service.UserHealthService;
import com.app.service.impl.UserHealthServiceImpl;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserHealthController {

    @Autowired
    UserHealthService emp;

    // 新增用户
    @RequestMapping(value = "/emp/adduser")
    public void AddUser(HttpServletRequest request){
        String str = request.getParameter("json_params");
        System.out.printf(str);
        emp.AddUser(str);
    }

    // 打卡用户
    @RequestMapping(value = "/emp/clock",method = RequestMethod.POST)
    public void PunchUser(UserHealth userHealth) {
        System.out.println(userHealth.toString());
        emp.PunchUser(userHealth);
    }

    //用户请假
    @RequestMapping(value = "/emp/leave",method = RequestMethod.POST)
    public void UserLeace(UserLeave userLeave){
        emp.UserLeace(userLeave);
    }

    // 查询用户
    @GetMapping("/emp/{user_name}")
    @ResponseBody
    public UserHealth GetUser(@PathVariable("user_name") String user_name){
        return emp.GetUser(user_name);
    }

    //用户新增药品
    @RequestMapping(value = "/emp/addDrug",method = RequestMethod.POST)
    public void AddDrug(@RequestBody String userDrug) throws JSONException {
        emp.addDrug(userDrug);
    }

    //用户新增菜品
    @RequestMapping(value = "/emp/addMeal",method = RequestMethod.POST)
    public void AddMeal(@RequestBody String userfood){
        System.out.println(userfood);
        emp.addFood(userfood);
    }

    //查询药品，分页查询【RequestParam，PathVariable，RequestBody（post消息体的）】
    @RequestMapping(value = "/emp/queryDrug",method = RequestMethod.POST)
    public List<UserDrug> queryDrug(QueryPage queryPage){
        System.out.println(queryPage.getPage()+queryPage.getRows()+queryPage.getOpenId());
        List<UserDrug> list = emp.queryDrug(queryPage.getPage(),queryPage.getRows(),queryPage.getOpenId());
        return list;
    }

    //查询药品总条数
    @RequestMapping(value = "/emp/drugNum",method = RequestMethod.POST)
    public Integer drugNum(String openId){
        return emp.drugNum(openId);
    }

}
