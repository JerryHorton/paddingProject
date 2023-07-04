package com.example.padding;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.example.padding.entity.City;
import com.example.padding.service.CityService;
import com.example.padding.utils.JedisUtils;
import com.example.padding.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * @version 1.0
 * @Date 2023/7/3 16:23
 * @Description
 * @Author Sxy
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JedisTest {
    @Autowired
    private JedisUtils jedisUtils;

    @Autowired
    private CityService cityService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testConnect() {
        Jedis jedis = jedisUtils.getJedis();
        String sxy = jedis.get("sxy");
        System.out.println(sxy);
        jedis.close();
    }

    @Test
    public void testZAdd1() {
        Jedis jedis = jedisUtils.getJedis();
        City city = new City();
        city.setId(1001L);
        city.setPid(1001L);
        city.setName("北京");
        String city_str = JsonUtils.object2Json(city);
        jedis.zadd("city", city.getId(), city_str);
        Set<Tuple> category = jedis.zrangeWithScores("city", 0, -1);
        for (Tuple tuple : category) {
            String sort = String.valueOf(tuple.getScore()).split("\\.")[0];
            String city_String = tuple.getElement();
            City city1 = JsonUtils.json2Object(city_String, City.class);
            log.info("sort:{} city:{}", sort, city1);
        }
    }

    @Test
    public void testZAdd2() {
        Jedis jedis = jedisUtils.getJedis();
        jedis.zadd("city", 1, "beijing");
        Set<Tuple> category = jedis.zrangeWithScores("city", 0, -1);
        for (Tuple tuple : category) {
            double sort = tuple.getScore();
            String value = tuple.getElement();
            log.info("sort:{} value:{}", sort, value);
        }
    }

    @Test
    public void testDel() {
        Jedis jedis = jedisUtils.getJedis();
        jedis.del("city");
    }

    @Test
    public void testDel1() {
        Jedis jedis = jedisUtils.getJedis();
        jedis.zrem("city", "{\"id\":1001,\"name\":\"北京\",\"pid\":1001}");
        Set<String> city = jedis.zrange("city", 0, -1);
        for (String s : city) {
            System.out.println(s);
        }
    }


    @Test
    public void testObject2Json() {
        Jedis jedis = jedisUtils.getJedis();
        City city = new City();
        city.setId(1L);
        city.setPid(1L);
        city.setName("北京");
        String city_str = JsonUtils.object2Json(city);
        jedis.set("beijing", city_str);
        String beijing = jedis.get("beijing");
        City city1 = JsonUtils.json2Object(beijing, City.class);
        System.out.println(city1);
    }

    @Test
    public void testObject2Json2z() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        City city = new City();
        city.setId(1L);
        city.setPid(1L);
        city.setName("北京");
        String city_str = JsonUtils.object2Json(city);
        operations.set("beijing", city_str);
        String beijing = operations.get("beijing");
        City city1 = JsonUtils.json2Object(beijing, City.class);
        System.out.println(city1);
    }

    @Test
    public void testFindAll1() {
        long startTime = System.currentTimeMillis();
        Jedis jedis = jedisUtils.getJedis();
        List<City> list;
        //优先从redis中查询，减少数据库查询次数达到优化
        Set<Tuple> category = jedis.zrangeWithScores("city", 0, -1);
        if (category == null || category.size() == 0) {     //若redis中没有数据则查询数据库并添加到redis
            list = cityService.list();
            for (City city : list) {
                String city_str = JsonUtils.object2Json(city);
                jedis.zadd("city", city.getId(), city_str);
            }
        } else {
            list = new ArrayList<>();
            for (Tuple tuple : category) {
                String element = tuple.getElement();
                City city = JsonUtils.json2Object(element, City.class);
                list.add(city);
            }
        }
        //结束时间
        long endTime = System.currentTimeMillis();
        //打印
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
    }

    @Test
    public void testFindAll2() {
        long startTime = System.currentTimeMillis();
        List<City> list = cityService.list();
        //结束时间
        long endTime = System.currentTimeMillis();
        //打印
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
    }
}
