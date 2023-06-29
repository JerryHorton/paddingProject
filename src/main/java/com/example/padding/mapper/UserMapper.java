package com.example.padding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.padding.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Date 2023/6/28 14:10
 * @Description 用户mapper
 * @Author Sxy
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
