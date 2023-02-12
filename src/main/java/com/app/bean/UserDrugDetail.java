package com.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Table;

/**
 *
 * 用于药品明细数据的实例
 * 主要用于用户药品明细表
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="用户药品明细表")
public class UserDrugDetail {
    //主键iD
    //用户唯一ID
    private String openId;
    //创建时间
    private String createTime;
    //药品uuid
    private String drugUuid;
    //药品名字
    private String drugName;
    //药品时间id
    private String subId;
    //订阅状态,1已订阅0未订阅
    private String subscriptionStatus;
    //corn表达式
    private String corn;
    //时间
    private String eatTime;
    //数量
    private String eatNum;
    //吃饭前后
    private String eatFrontBack;
    //具体描述
    private String detail;
}
