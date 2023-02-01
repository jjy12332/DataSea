package com.app.service.impl;

import com.app.service.QuartzManagerService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class QuartzManagerServiceImpl implements QuartzManagerService {
    private static final String JOB_GROUP_NAME = "cron_group"; //任务组名
    private static final String TRIGGER_GROUP_NAME = "cron_tigger"; //触发器组名

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean; //调度工厂

    public Scheduler getScheduled(){
        return schedulerFactoryBean.getScheduler();
    }

    /**
     * 添加定时任务
     * @param jobName 任务名称
     * @param jobClass 任务job
     * @param cron 表达式
     * @param params 任务job参数
     */
    public void addJob(String jobName, Class jobClass, String cron, HashMap<String,String> params){        //获取Scheduler
        try {
            Scheduler scheduler = getScheduled();
            //任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
            //传参数（可选）
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            if (params != null) {
                for (String key : params.keySet()) {
                    jobDataMap.put(key, params.get(key));
                }
            }
            //触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            //触发器名，触发器组
            triggerBuilder.withIdentity(jobName, TRIGGER_GROUP_NAME);
            triggerBuilder.startNow();
            //触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            //创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            //调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);
            //启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        }catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除定时任务
     * @param jobName 任务名称
     */
    public void removeJob(String jobName){
        try{
            Scheduler scheduler = getScheduled();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName,TRIGGER_GROUP_NAME);
            scheduler.pauseTrigger(triggerKey);//停止触发器
            scheduler.unscheduleJob(triggerKey);//移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName,JOB_GROUP_NAME));//删除任务
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }

    /**
     * 启动任务
     */
    public void startJobs(){
        try{
            Scheduler scheduler = getScheduled();
            scheduler.start();
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }

    /**
     * 停止任务
     */
    public void shutdownJobs(){
        try{
            Scheduler scheduler = getScheduled();
            if(!scheduler.isShutdown()){
                scheduler.shutdown();
            }
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }

    /**
     * 查询所有job任务
     * @return
     */
    public List<String> getAllJob(){
        List<String> jobs = new ArrayList<>();
        try{
            Set<JobKey> jobKeys = getScheduled().getJobKeys(GroupMatcher.anyGroup());
            for(JobKey jobKey:jobKeys){
                jobs.add(jobKey.getName());
            }
        }catch (SchedulerException e){
            e.printStackTrace();
        }
        return jobs;
    }
}
