package com.app.controller;

import com.app.bean.DoResult;
import com.app.service.impl.MyJob;
import com.app.service.impl.QuartzManagerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 定时任务控制类
 * 20230104
 */

@Slf4j
@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    QuartzManagerServiceImpl quartzManager;

    /**
     * 添加定时任务
     * @param jobName
     * @return
     */
    @GetMapping("/addJob")
    public DoResult addJob(String jobName){
        String cron = "0/30 * * * * ?";
        HashMap<String,String> map = new HashMap<>();
        map.put("jobName",jobName);
        quartzManager.addJob(jobName, MyJob.class,cron,map);
        log.info("job添加成功");
        return DoResult.success(200,"","");
    }

    /**
     * 删除job任务
     * @param jobName
     * @return
     */
    @GetMapping("/deleteJob")
    public DoResult deleteJob(String jobName){
        quartzManager.removeJob(jobName);
        log.info("删除job任务成功:"+jobName);
        return DoResult.success(200,"","");
    }

    /**
     * 获取所有jpb任务
     * @param jobName
     * @return
     */
    @GetMapping("/getAllJob")
    public DoResult getAllJob(){
        List<String> jobs = quartzManager.getAllJob();
        log.info("job列表："+jobs);
        return DoResult.success(200,"",jobs);
    }
}
