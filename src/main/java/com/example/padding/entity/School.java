package com.example.padding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private Long id;

    //省份id
    private Integer provId;

    //城市id
    private Integer cityId;

    //学校名称
    private String name;

    //地址
    private String address;

    //创建时间
    private Date createTime;
}
