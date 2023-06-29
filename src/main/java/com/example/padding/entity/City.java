package com.example.padding.entity;

import lombok.Data;

/**
 * @version 1.0
 * @Date 2023/6/28 14:11
 * @Description 城市实体类
 * @Author Sxy
 */

@Data
public class City {
    //城市id
    private Integer id;

    //城市名称
    private String name;

    //所属省份代码
    private Integer pid;
}
