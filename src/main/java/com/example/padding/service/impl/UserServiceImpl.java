package com.example.padding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.padding.entity.User;
import com.example.padding.mapper.UserMapper;
import com.example.padding.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2023/6/28 14:12
 * @Description 用户业务实现类
 * @Author Sxy
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
