package com.example.padding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.padding.entity.City;
import com.example.padding.mapper.CityMapper;
import com.example.padding.service.CityService;
import com.example.padding.utils.JedisUtils;
import com.example.padding.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @Date 2023/6/28 14:15
 * @Description 城市业务实现类
 * @Author Sxy
 */

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {
    @Autowired
    private CityService cityService;

    @Autowired
    private JedisUtils jedisUtils;

    /**
     * 根据pid查询城市信息或省份信息
     *
     * @param pid
     * @return
     */
    @Override
    public List<City> list(Long pid) {
        Jedis jedis = jedisUtils.getJedis();
        //确定查询对象为省份还是城市
        String key = pid == -1 ? "province" : "city_" + pid;
        List<City> list = null;
        try {
            //优先从redis中查询，减少数据库查询次数达到优化
            Set<Tuple> cities = jedis.zrangeWithScores(key, 0, -1);
            if (cities == null || cities.size() == 0) {     //若redis中没有数据则查询数据库并添加到redis
                LambdaQueryWrapper<City> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(City::getPid, pid);
                list = cityService.list(queryWrapper);
                for (City city : list) {
                    //保存入redis中
                    //保存序列化信息
                    String city_str = JsonUtils.object2Json(city);
                    jedis.zadd(key, city.getId(), city_str);
                }
            } else {    //若存在数据则从内存中读取
                list = new ArrayList<>();
                for (Tuple tuple : cities) {
                    //对象的Json串
                    String element = tuple.getElement();
                    //反序列化为对象
                    City city = JsonUtils.json2Object(element, City.class);
                    list.add(city);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            jedisUtils.close(jedis);
        }
        return list;
    }
}
