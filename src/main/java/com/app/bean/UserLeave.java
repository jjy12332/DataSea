package com.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="用户请假表")
public class UserLeave {
    // 主键iD
    //用户id(唯一键open_id)
    private String openId;
    //用户名称
    private String userName;
    //创建时间
    private String createTime;
    //请假类型
    private String leaveType;
    //请假原因
    private String leaveCase;
    //开始时间 到数据库为datetime类型
    private String startTime;
    //结束时间 到数据库为datetime类型
    private String endTime;


}
