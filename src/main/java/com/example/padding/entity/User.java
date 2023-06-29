package com.example.padding.entity;

import lombok.Data;

/**
 * @version 1.0
 * @Date 2023/6/28 14:11
 * @Description 用户实体类
 * @Author Sxy
 */

@Data
public class User {
    //用户id
    private Long id;

    //用户名
    private String username;

    //密码
    private String password;
}
