package com.example.padding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.padding.common.R;
import com.example.padding.entity.City;
import com.example.padding.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @Date 2023/6/28 15:25
 * @Description 城市控制器
 * @Author Sxy
 */

@Slf4j
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("list/{pid}")
    public R<List<City>> list(@PathVariable Integer pid) {
        LambdaQueryWrapper<City> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(City::getPid, pid);
        List<City> cities = cityService.list(queryWrapper);
        return R.success(cities);
    }
}
