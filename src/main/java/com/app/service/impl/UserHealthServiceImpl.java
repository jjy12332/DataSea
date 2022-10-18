package com.app.service.impl;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.app.bean.*;
import com.app.mapper.UserHealthMapper;
import com.app.service.UserHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserHealthServiceImpl implements UserHealthService {

    @Autowired
    private UserHealthMapper emp;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 新增用户
    public void AddUser(String str){
        //将前台传过来的字符串转为Json
        UserHealth a = JSON.parseObject(str, UserHealth.class);
        emp.insertData1(a);
    }

    // 打卡用户
    public void PunchUser(UserHealth userHealth) {
        /**
         *这边会抛出空指针异常
         * 目测是连接数据库的问题
         * */
        try{
            //将前台传过来的字符串转为Json
//            userHealth a = JSON.parseObject(str, userHealth.class);
            emp.insertData1(userHealth);
            System.out.println("打卡成功");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //用户请假
    public void UserLeace(UserLeave userLeave){
        /**
         * 请假分为俩种情况
         * 第一种是当天请假，只需要将当天此用户数据存放到请假表即可
         * 第二种是多天请假，需要将每天的数据放入到请假表中
         * 目前请假表为一张拉链表，后续会有一张请假总表生成，主要应对后续仓库的需求【待定】
         * 目前需要判断第一种还是第二种情况，如果第二种需要将日期数据进行拆分，且计算相应的插入条数
         *
         */

        /*注意，此处数据库有俩张表，如果主要区别是开始日期和结束日期类型不同，这边目前采用Varchar类型，后续变更在说*/
        //创建时间类对象，方便进行计算
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();

        //定义局部变量
        long long1,long2;
        Date date1,date2;
        int days = 0;

        //对时间进行计算
        try{
            date1 = format.parse(userLeave.getStartTime());
            date2 = format.parse(userLeave.getEndTime());
            long1 = date1.getTime();
            long2 = date2.getTime();
            days = (int)((long2 - long1) / (1000 * 60 * 60 * 24));
            System.out.println(days);
            calendar.setTime(date1);
        }catch (Exception e){
            e.printStackTrace();
        }

        List<com.app.bean.UserLeave> list = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            com.app.bean.UserLeave user = new UserLeave();
            user.setUserName(userLeave.getUserName());
            user.setLeaveType(userLeave.getLeaveType());
            user.setLeaveCase(userLeave.getLeaveCase());
            user.setStartTime(format.format(calendar.getTime()));
            user.setEndTime(format.format(calendar.getTime()));
            user.setOpenId(userLeave.getOpenId());
            calendar.add(calendar.DATE,1);
            list.add(user);
        }
        for(com.app.bean.UserLeave list1:list){
            System.out.println(list1);
        }
        emp.insertData2(list);

//        //如果开始时间和结束时间相等，那么直接插入数据到数据库
//        //userLeave.getStartTime().compareTo(userLeave.getEndTime())==0，这个也可以对时间进行比较，=0即是相等
//        if(days==0){
//            emp.InsertData2(list);
//        }else {
//            emp.InsertData2(list);
//        }


    }


    // 查询用户
    public UserHealth GetUser(String user_name){
        return emp.GetName(user_name);
    }

    //用户增加药品
    public void addDrug(String userDrug){
        JSONArray jsonArray = JSONArray.parseArray(userDrug);
        for(Object jo : jsonArray){
            UserDrug ud = ((JSONObject)jo).toJavaObject(UserDrug.class);

            //对eatTime字段进行处理，将【“早上”，“中午”，“晚上”】-> 早上，中午，晚上
            String a = ud.getEatTime().replace("[","").replace("]","").replace("\"","");
            ud.setEatTime(a);

            //获取时间段
            String[] b = ud.getEatTime().split(",");
            String tmp = "";
            //如果Detail字段不为空即可执行，为空
            if(b!=null) {
                for (int i = 0; i < b.length; i++) {
                    if (b[i].equals("早上")) {
                        String aa = b[i] + ud.getEatMorning() + "吃" + ud.getEatMorningNum() + "片药";
                        tmp = tmp.concat(aa);
                    } else if (b[i].equals("中午")) {
                        String aa = b[i] + ud.getEatNoon() + "吃" + ud.getEatNoonNum() + "片药";
                        tmp = tmp.concat(aa);
                    } else {
                        String aa = b[i] + ud.getEatNight() + "吃" + ud.getEatNightNum() + "片药";
                        tmp = tmp.concat(aa);
                    }
                    if (i < b.length - 1) tmp = tmp.concat(",");
                }
            }

            ud.setDetail(tmp);
            emp.insertDrug(ud);
        }
    }

    //返回药品
    public List<UserDrug> queryDrug(Integer page, Integer rows, String openId){
        Integer start=(page-1)*rows;
        List<UserDrug> pList= emp.queryDrug(start,rows,openId);

        return pList;
    }
    //返回药品总数
    public Integer drugNum(String openId){
        return emp.drugNum(openId);
    }

    //新增食物
    public void addFood(String userFood){
        JSONArray jsonArray = JSONArray.parseArray(userFood);
        for(Object jo : jsonArray) {
            UserFood uf = ((JSONObject) jo).toJavaObject(UserFood.class);
            emp.insertFood(uf);
        }


    }

}

