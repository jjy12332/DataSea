package com.app.controller;



import com.app.bean.UserFood;
import com.app.bean.UserHealth;
import com.app.bean.UserLeave;
import com.app.service.UserHealthService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public void AddDrug(@RequestBody String addDrug) throws JSONException {
        emp.AddDrug(addDrug);
    }

    //用户新增菜品
    @RequestMapping(value = "/emp/addMeal",method = RequestMethod.POST)
    public void AddMeal(@RequestBody UserFood userfood){
        emp.addFood(userfood);
    }
}
