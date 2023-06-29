package com.example.padding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.padding.entity.City;
import com.example.padding.mapper.CityMapper;
import com.example.padding.service.CityService;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2023/6/28 14:15
 * @Description 城市业务实现类
 * @Author Sxy
 */

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {
}
