package com.example.padding.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.padding.common.R;
import com.example.padding.entity.School;
import com.example.padding.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
