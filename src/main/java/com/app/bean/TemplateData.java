package com.app.bean;

/**
 *
 * 用户订阅的模板实例
 * 在WxPushTemplate实例中使用
 */
public class TemplateData {

    private String value;

    public TemplateData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
