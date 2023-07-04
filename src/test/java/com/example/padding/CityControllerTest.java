package com.example.padding;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.padding.common.R;
import com.example.padding.controller.CityController;
import com.example.padding.entity.City;
import com.example.padding.service.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @version 1.0
 * @Date 2023/7/4 10:51
 * @Description
 * @Author Sxy
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class CityControllerTest {
    @Autowired
    private CityController cityController;

    @Autowired
    private CityService cityService;

    @Test
    public void testList() {
        R<List<City>> list = cityController.list(-1L);
        List<City> data = list.getData();
        for (City datum : data) {
            System.out.println(datum);
        }
    }

    @Test
    public void testList1() {
        List<City>  list = cityService.list(130000L);
        for (City datum : list) {
            System.out.println(datum);
        }
    }
}
