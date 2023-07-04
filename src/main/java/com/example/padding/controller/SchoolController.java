package com.example.padding.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.padding.common.R;
import com.example.padding.entity.School;
import com.example.padding.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Date 2023/6/28 16:01
 * @Description 学校控制器
 * @Author Sxy
 */

@Slf4j
@RestController
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    /**
     * 学校信息的分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/list")
    public R<Page<School>> list(int page, int pageSize, String name) {
        log.info("page:{} pageSize:{}", page, pageSize);
        Page<School> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<School> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(name), School::getName, name);
        queryWrapper.orderByAsc(School::getId);
        schoolService.page(pageInfo);
        return R.success(pageInfo);
    }

    /**
     * 新增学校信息
     *
     * @param school
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody School school) {
        log.info("school:{}", school);
        schoolService.saveWithRedis(school);
        return R.success("学校信息添加成功");
    }

    /**
     * 根据id获取学校信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<School> getById(@PathVariable Long id) {
        log.info("schoolId:{}", id);
        School school = schoolService.getById(id);
        return R.success(school);
    }

    /**
     * 获取相应城市的所有学校信息
     *
     * @param cid
     * @return
     */
    @GetMapping("/{cid}")
    public R<List<School>> getByCid(@PathVariable Long cid) {
        log.info("cityId:{}", cid);
        List<School> schools = schoolService.get(cid);
        return R.success(schools);
    }

    /**
     * 修改学校信息，与学校关联信息可能也会受到影响，关联信息修改暂定
     *
     * @param school
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody School school) {
        schoolService.updateWith(school);
        return R.success("学校信息修改成功");
    }
}
