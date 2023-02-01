package com.app.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.app.Utils.DateUtil;
import com.app.Utils.TokenUtil;
import com.app.Utils.UuidUtil;
import com.app.bean.*;
import com.app.config.CommonConstants;
import com.app.mapper.UserHealthMapper;
import com.app.service.ApiInterfaceService;
import com.app.service.UserHealthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserHealthServiceImpl implements UserHealthService {

    @Autowired
    private UserHealthMapper emp;

    @Autowired
    private ApiInterfaceService ais;

    @Autowired
    JedisPool jedisPool;// = new Jedis("127.0.0.1", 6379);


    /**
     * 新增用户
     * 20221110
     */
    public DoResult AddUser(String jwt,String code, String userName,String userAvatar) throws Exception {

        Jedis jedis = jedisPool.getResource();

        //判断前端code是否成功传递到后端
        if (StringUtils.isAllEmpty(code)) {

            return DoResult.error(5001, "code获取失败", "");
        }
        //token不存在，新用户或者清楚缓存
        if (StringUtils.isBlank(jwt)) {

            return this.createToken(code, userAvatar, userName);
        } else {//token存在

            //解析token，拿到随机token，查验用户是否登录状态
            UserToken userToken = TokenUtil.getInfoFromToken(jwt);
            //检查随机token在redis中是否存在，
            if (jedis.exists(userToken.getUuidToken())) {
                //用户登录成功
                return DoResult.success(200, "老用户直接进入首页", jwt);
            } else {//随机token不存在（目前可以给用户重新获取token）

                return this.createToken(code, userAvatar, userName);
            }

        }
    }

    /**
     * 创建token
     *
     * @param code       前端获取用户code
     * @param userAvatar 用户头像
     * @param userName   用户名字
     * @return
     * @throws Exception
     */
    public DoResult createToken(String code, String userAvatar, String userName) throws Exception {
        Jedis jedis = jedisPool.getResource();
        /**
         *  封装url数据
         */
        //根据code获取openid
        String openIdUrl = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=%s", CommonConstants.appid, CommonConstants.secret, code, CommonConstants.grant_type_openId);
        //获取token
        String tokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&grant_type=%s", CommonConstants.appid, CommonConstants.secret, code, CommonConstants.grant_type_token);

        //获取用户openId
        DoResult doResult = ais.get(openIdUrl);
        String openId = (String) JSONObject.parseObject((String) doResult.getData()).get("openid");
        //判断openid是否获取成功，如果没有重新获取
        if (StringUtils.isEmpty(openId)) {
            return DoResult.error(5002, "获取openId失败", "null");
        }

        //检查数据库是否存在openId
        UserInfo userInfo = emp.selectUserInfo(openId);
        //如果不存在，新用户
        if (userInfo == null) {
            userInfo = (UserInfo) this.createUser(openId, userAvatar, userName).getData();
        }
        //存在则重新token
        //获取用户权限 1：普通用户 2：vip用户 3：admin用户
        UserToken userToken1 = new UserToken();
        //设置userToken，方便生成token
        userToken1.setUserId(userInfo.getOpenId());
        userToken1.setUserName(userInfo.getUserName());
        userToken1.setUuidToken(UuidUtil.uuid());
        userToken1.setPrivilegeId(userInfo.getPrivilegeId());
        userToken1.setTemp2("999");
        //生成token
        String token = TokenUtil.generateToken(userToken1, 0);

        //设置redis值 (健为token，值为用户openId)
        jedis.setex(userToken1.getUuidToken(), 3600,openId);
        return DoResult.success(200, "用户登录成功请检查", token);

    }

    /**
     * 创建新用户
     *
     * @param openId     用户openId
     * @param userAvatar 用户头像
     * @param userName   用户名字
     * @return
     */
    public DoResult createUser(String openId, String userAvatar, String userName) {
        UserInfo userInfo = new UserInfo();
        //设置用户opneId
        userInfo.setOpenId(openId);
        //用户是否给了用户名权限
        if (StringUtils.isBlank(userName)) {
            userInfo.setUserName(UuidUtil.uuid());
        } else {
            userInfo.setUserName(userName);
        }
        //用户是否给了头像权限
        if (StringUtils.isBlank(userAvatar)) {
            //给固定头像
            userInfo.setUserAvatar("https://echarts.apache.org/examples/data/asset/img/weather/cloudy_128.png");
        } else {
            userInfo.setUserAvatar(userAvatar);
        }
        //设置当前时间为注册时间
        userInfo.setCreateTime(String.valueOf(System.currentTimeMillis()));
        userInfo.setPhone("");
        userInfo.setToken("");
        //目前没有权限系统，所以这边所有用户都给1，后续增加，只需要调整此处即可
        userInfo.setPrivilegeId("1");
        System.out.println(userInfo);
        //插入用户信息表
        emp.insertUserInfo(userInfo);
        return DoResult.success(2000, "用户创建成功", userInfo);
    }


    // 打卡用户
    public DoResult PunchUser(UserHealth userHealth) {
        //从请求头中获取openId
        String openId = ((UserToken)RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();
        userHealth.setOpenId(openId);
        try {
            //将前台传过来的字符串转为Json
//            userHealth a = JSON.parseObject(str, userHealth.class);
            emp.insertUserHealth(userHealth);
            return DoResult.success("打卡成功","");
        } catch (Exception e) {
            return DoResult.error();
        }
    }

    //用户请假
    public DoResult UserLeace(UserLeave userLeave) {
        /**
         * 请假分为俩种情况
         * 第一种是当天请假，只需要将当天此用户数据存放到请假表即可
         * 第二种是多天请假，需要将每天的数据放入到请假表中
         * 目前请假表为一张拉链表，后续会有一张请假总表生成，主要应对后续仓库的需求【待定】
         * 目前需要判断第一种还是第二种情况，如果第二种需要将日期数据进行拆分，且计算相应的插入条数
         *
         */
        //从请求头中获取openId
        String openId = ((UserToken)RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();
        userLeave.setOpenId(openId);


        /*注意，此处数据库有俩张表，如果主要区别是开始日期和结束日期类型不同，这边目前采用Varchar类型，后续变更在说*/
        //创建时间类对象，方便进行计算
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();

        //定义局部变量
        long long1, long2;
        Date date1, date2;
        int days = 0;

        //对时间进行计算
        try {
            date1 = format.parse(userLeave.getStartTime());
            date2 = format.parse(userLeave.getEndTime());
            long1 = date1.getTime();
            long2 = date2.getTime();
            days = (int) ((long2 - long1) / (1000 * 60 * 60 * 24));
            System.out.println(days);
            calendar.setTime(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<com.app.bean.UserLeave> list = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            com.app.bean.UserLeave user = new UserLeave();
            user.setLeaveType(userLeave.getLeaveType());
            user.setLeaveCase(userLeave.getLeaveCase());
            user.setStartTime(format.format(calendar.getTime()));
            user.setEndTime(format.format(calendar.getTime()));
            user.setOpenId(userLeave.getOpenId());
            calendar.add(calendar.DATE, 1);
            list.add(user);
        }
        for (com.app.bean.UserLeave list1 : list) {
            System.out.println(list1);
        }
        emp.insertData2(list);

        return DoResult.success("请假成功","");
        //如果开始时间和结束时间相等，那么直接插入数据到数据库
//        //userLeave.getStartTime().compareTo(userLeave.getEndTime())==0，这个也可以对时间进行比较，=0即是相等
//        if(days==0){
//            emp.InsertData2(list);
//        }else {
//            emp.InsertData2(list);
//        }
    }



    // 查询用户
//    public UserHealth GetUser(String user_name) {
//        return emp.GetName(user_name);
//    }

    //用户增加药品
    public DoResult addDrug(String userDrug) {

        //从请求头中获取openId
        String openId = ((UserToken)RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();

        JSONArray jsonArray = JSONArray.parseArray(userDrug);
        for (Object jo : jsonArray) {
            //将数据插入用户药品实例中
            UserDrug ud = ((JSONObject) jo).toJavaObject(UserDrug.class);

            //对eatTime字段进行处理，将【“早上”，“中午”，“晚上”】-> 早上，中午，晚上
            String a = ud.getEatTime().replace("[", "").replace("]", "").replace("\"", "");
            ud.setEatTime(a);

            ud.setDrugUuid(UuidUtil.uuid());
            ud.setOpenId(openId);
            ud.setEndTime(DateUtil.strToDate(ud.getCreateTime(), Integer.parseInt(ud.getEatDays())));

            //形成用户药品明细表数据
            this.insertDrugDetail(ud);
            //插入到药品表中
            emp.insertDrug(ud);
        }
        return DoResult.success("药品增加成功","");
    }

    public DoResult insertDrugDetail(UserDrug userDrug){
        List<UserDrugDetail> list = new ArrayList<>();
        if(StringUtils.isBlank(userDrug.getEatTime())){
            return DoResult.error();
        }
        //拿到 早上 中午 晚上 的数据 进行解析
        String[] arrays = userDrug.getEatTime().split(",");
        for(int i=0 ; i<arrays.length;i++){
            //创建UserDrugDetail实例
            UserDrugDetail userDrugDetail = new UserDrugDetail();
            //给实例赋值
            userDrugDetail.setOpenId(userDrug.getOpenId());
            userDrugDetail.setCreateTime(userDrug.getCreateTime());
            userDrugDetail.setDrugUuid(userDrug.getDrugUuid());
            userDrugDetail.setDrugName(userDrug.getDrugName());
            if (arrays[i].equals("早上")) {
                userDrugDetail.setEatTime(userDrug.getTimeMorning());
                userDrugDetail.setCorn(DateUtil.getCron(userDrug.getTimeMorning()));
                userDrugDetail.setEatNum(userDrug.getEatMorningNum());
                userDrugDetail.setDetail(arrays[i] + userDrug.getEatMorning() + "吃" + userDrug.getEatMorningNum() + "片药");
            } else if (arrays[i].equals("中午")) {
                userDrugDetail.setEatTime(userDrug.getTimeNoon());
                userDrugDetail.setCorn(DateUtil.getCron(userDrug.getTimeNoon()));
                userDrugDetail.setEatNum(userDrug.getEatNoonNum());
                userDrugDetail.setDetail(arrays[i] + userDrug.getEatNoon() + "吃" + userDrug.getEatNoonNum() + "片药");
            } else {
                userDrugDetail.setEatTime(userDrug.getTimeNight());
                userDrugDetail.setCorn(DateUtil.getCron(userDrug.getTimeNight()));
                userDrugDetail.setEatNum(userDrug.getEatNightNum());
                userDrugDetail.setDetail(arrays[i] + userDrug.getEatNight() + "吃" + userDrug.getEatNightNum() + "片药");
            }
            emp.insertDrugDetail(userDrugDetail);

        }

        return DoResult.success();
    }

    //返回药品
    public DoResult queryDrug(QueryPage queryPage) {
        //从请求头中获取openId
        String openId = ((UserToken)RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();
        Integer start = (queryPage.getPage() - 1) * queryPage.getRows();
        List<UserDrug> pList = emp.queryDrug(start, queryPage.getRows(), openId);
        return DoResult.success(pList);
    }

    //返回药品总数
    public DoResult drugNum() {
        //从请求头中获取openId
        String openId = ((UserToken)RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();
        int num = emp.drugNum(openId);
        return DoResult.success("获取总数为"+num,num);
    }


    //用户订阅功能（药品订阅）
    public DoResult subscription(String drugUuid, String status) {

        //从请求头中获取openId
        String openId = ((UserToken)RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();
        try{

            emp.updateSubscription(openId,drugUuid,status);
            if("1".equals(status)){
                return DoResult.success(200,"药品订阅成功","1");
            }
            return DoResult.success(200,"药品订阅成功","0");

        }catch (Exception e){
            return DoResult.error(500,"药品订阅失败","");
        }

    }


//    //用户订阅功能（药品订阅）
//    public DoResult subscription(String drugUuid, String status) {
//
//        //从请求头中获取openId
//        String openId = ((UserToken)RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();
//
//        //封装获取token的url
//        String openIdUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=%s&appid=%s&secret=%s", CommonConstants.grant_type_token,CommonConstants.appid, CommonConstants.secret);
//
//        //获取token (获取小程序全局唯一后台接口调用凭据（access_token）)
//        Object object = ais.get(openIdUrl).getData();
//        String token = (String) JSONObject.parseObject((String) object).get("access_token");
//        if(token == null){
//            return DoResult.error(500,"获取access_token失败","");
//        }
//
//
//        //订阅模板
//        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + token;
//        //拼接推送的模版
//        WxPushTemplate wxMsgDto = new WxPushTemplate();
//        wxMsgDto.setTouser(openId);
//        wxMsgDto.setTemplate_id(CommonConstants.model_id);
//
//        //获取用户准备订阅的药品信息
//        UserDrug userDrug = emp.queryDrugId(openId, drugUuid);
////        List<Map<String, TemplateData>> listMap = new ArrayList<>();
//
//
//        String[] b = userDrug.getEatTime().split(",");
//        Map<String, TemplateData> map = new LinkedHashMap<>(5);
//
//        //药品名字
//        map.put("thing1", new TemplateData("jjy"));
//        //服用时间
//        map.put("time2", new TemplateData("2022-01-13 02:31:36"));
//        //服用剂量
//        map.put("thing3", new TemplateData("1"));
//        //用药周期
//        map.put("time11", new TemplateData("2022-01-13 02:31:36"));
//        //用药建议
//        map.put("thing4", new TemplateData("早上1片"));
//
//        wxMsgDto.setData(map);
////        for (int i = 0; i < b.length; i++) {
////            Map<String, String> map = new LinkedHashMap<>(5);
////
////            //药品名字
////            map.put("thing1",userDrug.getDrugName());
////
////
////
////            if("早上".equals(b[i])){
////                //服用时间
////                map.put("time2",userDrug.getTimeMorning());
////                //服用剂量
////                map.put("thing3",userDrug.getEatMorningNum());
////            }
////            if("中午".equals(b[i])){
////                //服用时间
////                map.put("time2",userDrug.getTimeNoon());
////                //服用剂量
////                map.put("thing3",userDrug.getEatNoonNum());
////            }
////            if("晚上".equals(b[i])){
////                //服用时间
////                map.put("time2",userDrug.getTimeNight());
////                //服用剂量
////                map.put("thing3",userDrug.getEatNightNum());
////            }
////
////            //用药周期
////            map.put("time11",userDrug.getCreateTime());
////            //用药建议
////            map.put("thing4",userDrug.getDetail());
////
////
////            listMap.add(map);
////        }
////        listMap.add(map);
//
//        System.out.println(System.currentTimeMillis());
//
//        //开始发送推送api（此处循环发送并确保都订阅成功）
////        for (int i = 0; i < listMap.size(); i++) {
//            try {
////                wxMsgDto.setData(listMap.get(i));
//                ais.postKeyValue(url, wxMsgDto);
//            } catch (Exception e) {
//                e.printStackTrace();
////                return DoResult.error("订阅失败，失败数量为" + Math.abs(i - 3));
//            }
////        }
//
//
//        return DoResult.success();
//    }


    //新增食物
    public DoResult addFood(String userFood) {
        //从请求头中获取openId
        String openId = ((UserToken)RequestContextHolder.getRequestAttributes().getAttribute("userId",0)).getUserId();

        JSONArray jsonArray = JSONArray.parseArray(userFood);
        try{
            for (Object jo : jsonArray) {
                UserFood uf = ((JSONObject) jo).toJavaObject(UserFood.class);
                uf.setOpenId(openId);
                emp.insertFood(uf);
            }
            return DoResult.success("食物增加成功","");
        }catch (Exception e){
            return DoResult.error("食物增加失败","");
        }

    }

    /**
     *
     * @param
     * @return
     */
    public DoResult lookFile() {
        JSONObject jsonObject = new JSONObject();
        //调用脚本
        List<String> list = exceShell("sh /home/icbc/bin/jjy.sh");
        //读取文本内容
        String fileName = "/home/icbc/bin/jjy.txt";
        Path path = Paths.get(fileName);
        try {
            byte[] bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            DoResult.error("服务异常联系你弟1","");
        }
        try {
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for(int i=0;i<allLines.size();i++){
                String[] a = allLines.get(i).split(":");
                jsonObject.put(a[0],a[1]);
            }

            delLinuxFile("rm -f /home/icbc/bin/jjy.txt");
            return DoResult.success(jsonObject);
        } catch (IOException e) {
            DoResult.error("服务异常联系你弟2","");
        }
        return DoResult.error("服务异常联系你弟3","");
    }



    /**
     * 脚本路径或者命令
     * @param pathOrCommand
     * @return
     */
    public  List<String>  exceShell(String pathOrCommand){
        ArrayList<String> list = new ArrayList<>();
        try{
            Process exec = Runtime.getRuntime().exec(pathOrCommand);
            int i = exec.waitFor();
            if(0!=i){
                list.add("执行错误，error code :"+i);
            }
            BufferedInputStream inputStream = new BufferedInputStream(exec.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String li=null;
            while ((li=reader.readLine())!=null){
                list.add(li);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    public void delLinuxFile(String filePath) {
//        String cmd = "rm -f" + filePath;//linux指令
        try {
            Process process = Runtime.getRuntime().exec(filePath);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

