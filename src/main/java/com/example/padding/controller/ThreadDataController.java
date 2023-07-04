package com.example.padding.controller;

import com.example.padding.common.R;
import com.example.padding.entity.ThreadData;
import com.example.padding.service.ThreadDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Date 2023/7/4 15:38
 * @Description
 * @Author Sxy
 */
@Slf4j
@RestController
@RequestMapping("/data")
public class ThreadDataController {
    @Autowired
    private ThreadDataService threadDataService;

    @PostMapping
    public R<String> save(@RequestBody ThreadData threadData) {
        log.info("threadData:{}", threadData);
        threadDataService.save(threadData);
        return R.success("添加成功");
    }
}
