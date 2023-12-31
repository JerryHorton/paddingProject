package com.example.padding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.padding.entity.School;

import java.util.List;

/**
 * @version 1.0
 * @Date 2023/6/28 15:58
 * @Description 学校业务接口
 * @Author Sxy
 */

public interface SchoolService extends IService<School> {
    void updateWith(School school);

    List<School> get(Long cid);

    void saveWithRedis(School school);
}
