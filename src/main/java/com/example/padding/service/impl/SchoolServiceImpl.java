package com.example.padding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.padding.entity.School;
import com.example.padding.mapper.SchoolMapper;
import com.example.padding.service.SchoolService;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2023/6/28 15:59
 * @Description 学校业务实现类
 * @Author Sxy
 */

@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {
}
