package com.example.padding.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @Date 2023/7/4 15:18
 * @Description 线索信息
 * @Author Sxy
 */

@Data
public class ThreadData {
    //id
    private Long id;

    //省份id
    private Long provId;

    //省份名称
    private String provName;

    //城市id
    private Long cityId;

    //城市名称
    private String cityName;

    //姓名
    private String name;

    //座机号
    private String mobile;

    //手机号
    private String phone;

    //QQ
    private String qq;

    //学校id
    private Long schoolId;

    //学校名称
    private String schoolName;

    //身份证号
    private String idNumber;

    //准考证号
    private String examNumber;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
