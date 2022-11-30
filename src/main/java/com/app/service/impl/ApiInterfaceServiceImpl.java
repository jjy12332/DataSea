package com.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.bean.DoResult;
import com.app.service.ApiInterfaceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sun.misc.Unsafe;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 各类API接口调用
 *
 * 学习mono和WebClient
 *
 *    WebClient是Spring提供的非阻塞、响应式的Http客户端，提供同步及异步的API，将会代替RestTemplate及AsyncRestTemplate；
 *
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

    //如果相同域名则可以使用这个固定域名，目前不启用
//    private WebClient webClient = WebClient.create("https://api.weixin.qq.com");

    WebClient webClient = WebClient.create();


    /*
     * 接口请求调用（旧版）
     */
    public DoResult interfaceAPI(String url, Object params,String type) {
            //调用接口的方法：在项目的工具包下导入HttpClientUtil这个工具类，或者也可以使用Spring框架的restTemplate来调用
            RestTemplate restTemplate = new RestTemplate();

            //调用接口
            ResponseEntity<String> responseEntity = null;
            try {
                if("post".equals(type)) {
                    responseEntity = restTemplate.postForEntity(new URI(url), params, String.class);
                }
                if("get".equals(type)){
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
        Unsafe.getUnsafe();
        return DoResult.success(object);
    }





    /**
     * GET请求
     */
    public  DoResult get(String url) {
        //同步方式
//        Mono<String> mono = webClient.get().uri(url).retrieve().bodyToMono(String.class).retry(0);
//        String temp = mono.block();
        return DoResult.success(webClient.get().uri(url).retrieve().bodyToMono(String.class).retry(3).block());
        //异步方式
//        final CountDownLatch latch = new CountDownLatch(5);
//        for (int i = 0; i < 5; i++) {
//            requestPath = "http://localhost:8080/demo/httptest/getUser?userId=1000&userName=李白" + i;
//            mono = webClient.get().uri(requestPath).retrieve().bodyToMono(String.class);
//            mono.subscribe(new Consumer<String>() {
//                @Override
//                public void accept(String s) {
//                    latch.countDown();
//                    System.out.println("get subscribe返回结果：" + s);
//                }
//            });
//        }
//
//        try {
//            latch.await();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * POST请求(发送键值对数据）
     */
    public DoResult postKeyValue(String url,Object params) {
//        String requestPath = "http://localhost:8080/demo/httptest/getUser";
//        WebClient webClient = WebClient.create();
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//        map.add("userId", "1000");
//        map.add("userName", "李白");
        Mono<String> mono = webClient.post().uri(url).bodyValue(params).retrieve().bodyToMono(String.class);
        System.out.println("post返回结果：" + mono.block());
        System.out.println("post返回结果：" + mono.block());
        return DoResult.success(mono.block());
    }


    /**
     * POST请求(发送JSON数据）
     */
    public DoResult postJson(String url,String params) {
//        String requestPath = "http://localhost:8080/demo/httptest/addUser";
        WebClient webClient = WebClient.create();
//        String param = "{\"userId\": \"1001\",\"userName\":\"杜甫\"}";
        Mono<String> mono = webClient.post().uri(url).contentType(MediaType.APPLICATION_JSON).bodyValue(params)
                .retrieve().bodyToMono(String.class);
        System.out.println("post json返回结果：" + mono.block());
        return DoResult.success();
    }


}
