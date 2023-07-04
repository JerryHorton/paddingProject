package com.example.padding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.padding.entity.ThreadData;
import com.example.padding.mapper.ThreadDataMapper;
import com.example.padding.service.ThreadDataService;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2023/7/4 15:33
 * @Description 线索信息业务实现类
 * @Author Sxy
 */

@Service
public class ThreadDataServiceImpl extends ServiceImpl<ThreadDataMapper, ThreadData> implements ThreadDataService {
}
