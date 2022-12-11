package com.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 继承用户药品表，返回结果集
 *
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDrugResult extends UserDrug{
    //订阅状态,1已订阅0未订阅
    private String subscriptionStatus;
}
