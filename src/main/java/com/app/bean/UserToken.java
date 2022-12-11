package com.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * 用于生成token和解析token的实例
 */
@Data
@ToString
@NoArgsConstructor
public class UserToken {
    private static final long serialVersionUID = 1L;

    public UserToken(String userName, String userId, String privilegeId) {
        this.userId = userId;
        this.userName = userName;
        this.privilegeId = privilegeId;
    }

    public UserToken(String userName, String userId, String privilegeId, String uuidToken,String temp2) {
        this.userId = userId;
        this.userName = userName;
        this.privilegeId = privilegeId;
        this.uuidToken = uuidToken;
        this.temp2 = temp2;
    }

    /**
     * 用户id（openId）
     */
    private String userId;
    /**
     * 用户登录名（微信名）
     */
    private String userName;
    /**
     * 权限id（权限id）
     */
    private String privilegeId;

    /**
     * 随机token用于判断用户登录状态
     */
    private String uuidToken;

    /**
     * 冗余字段
     */
    private String temp2;



    @Override
    public String toString() {
        return "UserToken{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", privilegeId='" + privilegeId + '\'' +
                ", temp1='" + uuidToken + '\'' +
                ", temp2='" + temp2 + '\'' +
                '}';
    }

}
