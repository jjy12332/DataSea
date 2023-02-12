package com.app.controller;


import com.app.bean.*;
import com.app.service.UserHealthService;
import org.apache.ibatis.annotations.Param;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

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
    @RequestMapping(value = "/emp/adduser",method = RequestMethod.POST)
    public DoResult AddUser(String jwt,String code, String userName,String userAvatar) throws Exception {
        System.out.printf(code);
        DoResult doResult = emp.AddUser(jwt,code,userName,userAvatar);
        System.out.println(doResult);
        return doResult;
    }

    // 打卡用户
    @RequestMapping(value = "/emp/clock", method = RequestMethod.POST)
    public DoResult PunchUser(UserHealth userHealth) {
        System.out.println(userHealth.toString());
        return emp.PunchUser(userHealth);
    }

    //用户请假
    @RequestMapping(value = "/emp/leave", method = RequestMethod.POST)
    public DoResult UserLeace(UserLeave userLeave) {
        return emp.UserLeace(userLeave);
    }

    // 查询用户
//    @GetMapping("/emp/{user_name}")
//    @ResponseBody
//    public UserHealth GetUser(@PathVariable("user_name") String user_name) {
//        return emp.GetUser(user_name);
//    }

    //用户新增药品
    @RequestMapping(value = "/emp/addDrug", method = RequestMethod.POST)
    public DoResult AddDrug(UserDrug userDrug) {
        return emp.addDrug(userDrug);
    }

    //删除药品
    @RequestMapping(value = "/emp/deleteDrug", method = RequestMethod.POST)
    public DoResult deleteDrug(@RequestParam String drugUuid) {
        return emp.deleteDrug(drugUuid);
    }

    //修改药品天数
    @RequestMapping(value = "/emp/updateDrugDays", method = RequestMethod.POST)
    public DoResult updateDrugDays(@RequestParam String drugUuid,String eatDays,String createTime) {
        return emp.updateDrugDays(drugUuid,eatDays,createTime);
    }

    //用户新增药品详细信息
    @RequestMapping(value = "/emp/addDrugDetail", method = RequestMethod.POST)
    public DoResult AddDrugDetail(UserDrugDetail userDrugDetail) {//TODO 重写
        return emp.addDrugDetail(userDrugDetail);
    }

    //删除药品详细信息时间
    @RequestMapping(value = "/emp/deleteDrugDetailDate", method = RequestMethod.POST)
    public DoResult deleteDrugDetailDate(@RequestParam String drugUuid,String subId) {
        return emp.deleteDrugDetailDate(drugUuid,subId);
    }

    //用户新增菜品
    @RequestMapping(value = "/emp/addMeal", method = RequestMethod.POST)
    public DoResult AddMeal(@RequestBody String userfood) { //TODO 重写
        return emp.addFood(userfood);
    }

    //查询药品，分页查询【RequestParam，PathVariable，RequestBody（post消息体的）】
    @RequestMapping(value = "/emp/queryDrug", method = RequestMethod.POST)
    public DoResult queryDrug(QueryPage queryPage) {
        return emp.queryDrug(queryPage);
    }

    //查询药品总条数
    @RequestMapping(value = "/emp/drugNum", method = RequestMethod.POST)
    public DoResult drugNum() {
        return emp.drugNum();
    }

    //查询药品详细信息
    @RequestMapping(value = "/emp/queryDrugDetail", method = RequestMethod.POST)
    public DoResult queryDrugDetail(@RequestParam String drugUuid) {
        return emp.queryDrugDetail(drugUuid);
    }

    //
    //用户订阅状态接口
    @RequestMapping(value = "/emp/subscription", method = RequestMethod.POST)
    public DoResult subscription(@RequestParam String drugUuid,String subId,String status) {
        DoResult doResult = emp.subscription(drugUuid,subId,status);
        return doResult;
    }

    //给老姐的接口
    @RequestMapping(value = "emp/lookFile", method = RequestMethod.POST)
    public DoResult lookFile(){
        return emp.lookFile();
    }

}
