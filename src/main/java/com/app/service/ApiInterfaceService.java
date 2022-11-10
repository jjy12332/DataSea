package com.app.service;

import com.app.bean.DoResult;

import java.util.Map;

public interface ApiInterfaceService {

    /*
     * 接口请求调用
     * 参数一：接口地址
     * 参数二：存接口中对应的参数
     * 参数三: 请求类型post或者get
     */
    DoResult interfaceAPI(String url, Object params,String type);

}
