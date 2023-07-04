package com.example.padding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.padding.entity.City;

import java.util.List;

/**
 * @version 1.0
 * @Date 2023/6/28 14:15
 * @Description 城市业务接口
 * @Author Sxy
 */

public interface CityService extends IService<City> {
    List<City> list(Long pid);
}
