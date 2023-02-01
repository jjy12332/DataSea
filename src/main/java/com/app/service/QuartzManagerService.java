package com.app.service;

import java.util.HashMap;
import java.util.List;

public interface QuartzManagerService {

    /**
     * 添加定时任务
     * @param jobName 任务名称
     * @param jobClass 任务job
     * @param cron 表达式
     * @param params 任务job参数
     */
    void addJob(String jobName, Class jobClass, String cron, HashMap<String,String> params);

    /**
     * 删除定时任务
     * @param jobName 任务名称
     */
    void removeJob(String jobName);


    /**
     * 启动任务
     */
    void startJobs();

    /**
     * 停止任务
     */
    void shutdownJobs();

    /**
     * 查询所有job任务
     * @return
     */
    List<String> getAllJob();
}
