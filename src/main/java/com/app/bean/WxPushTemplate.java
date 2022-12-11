package com.app.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 *
 * 微信订阅模板
 * 与TemplateData配合使用
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WxPushTemplate {

    //用户openId
    private String touser;

    //订阅模板id
    private  String template_id;

    //消息跳转页面
    private String page;

    //推送模板的内容
    private Map<String,TemplateData> data;

}
