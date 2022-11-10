package com.app.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * 微信推送模板
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WxPushTemplate {

    //用户openId
    private String openId;

    //订阅模板id
    private  String templateId;

    //消息跳转页面
    private String page;

    //推送模板的内容
    private Map<String,String> data;

}