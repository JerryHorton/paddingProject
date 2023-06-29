package com.example.padding.entity;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2023/6/28 15:53
 * @Description 学校实体类
 * @Author Sxy
 */

@Data
public class School {
    //学校id
    private Integer id;

    //省份id
    private Integer provId;

    //城市id
    private Integer cityId;

    //学校名称
    private String name;

    //地址
    private String address;

    //学校类型
    private Integer schoolType;

    //校长
    private String master;

    //电话
    private String telephone;

    //评论
    private String remark;

    //创建时间
    private Date createTime;
}
