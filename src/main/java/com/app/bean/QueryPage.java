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
@Table(name="分页查询")
public class QueryPage {
    //查询页数
    private Integer page;
    //查询行数
    private Integer rows;
}
