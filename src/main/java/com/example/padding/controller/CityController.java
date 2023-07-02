package com.example.padding.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public R<List<City>> list(@PathVariable Long pid) {
        LambdaQueryWrapper<City> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(City::getPid, pid);
        List<City> cities = cityService.list(queryWrapper);
        return R.success(cities);
    }

    /**
     * 城市信息的分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/list")
    public R<Page<City>> list(int page, int pageSize, String name) {
        log.info("page:{} pageSize:{}", page, pageSize);
        Page<City> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<City> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(name), City::getName, name);
        queryWrapper.orderByAsc(City::getId);
        cityService.page(pageInfo);
        return R.success(pageInfo);
    }
}
