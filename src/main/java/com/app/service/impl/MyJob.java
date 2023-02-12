package com.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.bean.TemplateData;
import com.app.bean.UserToken;
import com.app.bean.WxPushTemplate;
import com.app.config.CommonConstants;
import com.app.service.ApiInterfaceService;
import com.app.service.UserHealthService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class MyJob implements Job {

    @Autowired
    UserHealthService userHealthService;
    @Autowired
    private ApiInterfaceService ais;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        JobDetail a = jobExecutionContext.getJobDetail();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        HashMap<String,String> hashMap = new HashMap<>();
        for(Object map : jobDataMap.keySet()){
            hashMap.put((String) map,jobDataMap.getString((String) map));
        }

        String jobName = (String) jobDataMap.get("jobName");
        System.out.println(hashMap.get("drugName")+hashMap.get("eatTime")+hashMap.get("eatNum")+hashMap.get("detail"));

        //调用微信推送接口
        userHealthService.subscriptionDrug(hashMap.get("openId"),hashMap.get("drugName"),hashMap.get("eatTime"),hashMap.get("eatNum"),hashMap.get("detail"));




        //---------------------------------------------------------------------------
//        //从请求头中获取openId
//        String openId = ((UserToken) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0)).getUserId();
//
//        //封装获取token的url
//        String openIdUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=%s&appid=%s&secret=%s", CommonConstants.grant_type_token, CommonConstants.appid, CommonConstants.secret);
//
//        //获取token (获取小程序全局唯一后台接口调用凭据（access_token）)
//        //注意：！！！！！！订阅一次就要将token放入redis中，判断token的生命周期 目前没有实现
//        Object object = ais.get(openIdUrl).getData();
//        String token = (String) JSONObject.parseObject((String) object).get("access_token");
//        if (token == null) {
//            log.info("获取access_token失败");
//            return;
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
//        //定义模板的Map LinkedHashMap可以进行排序
//        Map<String, TemplateData> map = new LinkedHashMap<>(5);
//
//        //药品名字
//        map.put("thing1", new TemplateData(hashMap.get("drugName")));
//        //服用时间
//        map.put("time2", new TemplateData(hashMap.get("eatTime")));
//        //服用剂量
//        map.put("thing3", new TemplateData(hashMap.get("eatNum")));
//        //用药周期
//        map.put("time11", new TemplateData(hashMap.get("eatTime")));
//        //用药建议
//        map.put("thing4", new TemplateData(hashMap.get("detail")));
//
//        wxMsgDto.setData(map);
//
//        //开始发送推送api
//        try {
//            ais.postKeyValue(url, wxMsgDto);
//        } catch (Exception e) {
//            log.info("调用推送api失败，错误信息："+e);
//            return;
//        }
        //------------------------------------------------------------------------






        log.info("执行任务时间："+simpleDateFormat.format(new Date())+",jobName："+jobName+",jobPrams："+ JSON.toJSONString(hashMap));





    }
}
