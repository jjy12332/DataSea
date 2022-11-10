package com.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.bean.DoResult;
import com.app.bean.UserDrug;
import com.app.service.ApiInterfaceService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.mockito.internal.util.StringUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


/**
 * 各类API接口调用
 */
@Service
public class ApiInterfaceServiceImpl implements ApiInterfaceService {


    /*
     * 小程序缓存的access_token
     */
//    private static String accessToken;

    /**
     * 小程序access_token的失效时间
     */
//    private static long expiresTime;




    /*
     * 接口请求调用
     */
    public DoResult interfaceAPI(String url, Object params,String type) {
            //调用接口的方法：在项目的工具包下导入HttpClientUtil这个工具类，或者也可以使用Spring框架的restTemplate来调用
            RestTemplate restTemplate = new RestTemplate();

            //调用接口
            ResponseEntity<String> responseEntity = null;
            try {
                if("get".equals(type)) {
                    responseEntity = restTemplate.postForEntity(new URI(url), params, String.class);
                }
                if("post".equals(type)){
                    responseEntity = restTemplate.getForEntity(url, String.class, params);
                }
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            //获取消息体
            String body = responseEntity.getBody();
            JSONObject object = JSON.parseObject(body);
            Integer errcode = object.getInteger("errcode");
            if (errcode != null && errcode != 0) {
                String errmsg = object.getString("errmsg");
                System.out.println("请求accessToken失败，返回码：" + errcode + "，错误信息：" + errmsg);
                // 返回异常
                return DoResult.error(errcode,errmsg,null);
            }
        return DoResult.success(object);
    }



//    public DoResult interfaceGet(String url, Map<String, String> params) {
//        System.out.println(accessToken);
//        if (StringUtils.isAllBlank(accessToken)) {
//
//            if (expiresTime < System.currentTimeMillis()) {
//                //调用接口的方法：在项目的工具包下导入HttpClientUtil这个工具类，或者也可以使用Spring框架的restTemplate来调用
//                RestTemplate restTemplate = new RestTemplate();
//
//                //调用接口
//                ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, params);
//                //获取消息体
//                String body = responseEntity.getBody();
//                JSONObject object = JSON.parseObject(body);
//                Integer errcode = object.getInteger("errcode");
//                if (errcode != null && errcode != 0) {
//                    String errmsg = object.getString("errmsg");
//                    System.out.println("请求accessToken失败，返回码：" + errcode + "，错误信息：" + errmsg);
//                    // 返回异常
//                    return DoResult.error(errcode, errmsg, null);
//                }
//                // 缓存accessToken
//                accessToken = object.getString("access_token");
//                // 设置accessToken的失效时间
//                long expires_in = object.getLong("expires_in");
//                // 失效时间 = 当前时间 + 有效期(提前一分钟，也可不提前，这里只是稳妥一下)
//                expiresTime = System.currentTimeMillis() + (expires_in - 60) * 1000;
//                return DoResult.success(object.getString("access_token"));
//
//            }
//            return DoResult.error("token已过期", null);
//        }
//        return DoResult.success(accessToken);
//    }
}
