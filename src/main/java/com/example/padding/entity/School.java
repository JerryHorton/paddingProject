package com.example.padding.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.time.LocalDateTime;

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
    private Long provId;

    //城市id
    private Long cityId;

    //学校名称
    private String name;

    //地址
    private String address;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
