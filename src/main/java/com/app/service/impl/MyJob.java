package com.app.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Slf4j
public class MyJob implements Job {

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


        log.info("执行任务时间："+simpleDateFormat.format(new Date())+",jobName："+jobName+",jobPrams："+ JSON.toJSONString(hashMap));
    }
}
