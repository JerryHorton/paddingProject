package com.example.padding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.padding.entity.City;
import com.example.padding.entity.School;
import com.example.padding.mapper.SchoolMapper;
import com.example.padding.service.SchoolService;
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
 * @Date 2023/6/28 15:59
 * @Description 学校业务实现类
 * @Author Sxy
 */

@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private JedisUtils jedisUtils;

    /**
     * 修改学校信息
     *
     * @param school
     */
    @Override
    public void updateWith(School school) {
        schoolService.updateById(school);
        Jedis jedis = jedisUtils.getJedis();
        try {
            //school_cid作为key
            String key = "school_" + school.getCityId();
            //删除redis原保存的数据
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            jedisUtils.close(jedis);
        }
    }

    /**
     * 查询cid城市的所有学校信息
     *
     * @param cid
     * @return
     */
    @Override
    public List<School> get(Long cid) {
        Jedis jedis = jedisUtils.getJedis();
        List<School> list = null;
        try {
            //school_cid作为key
            String key = "school_" + cid;
            //优先从redis中查询，减少数据库查询次数达到优化
            Set<Tuple> schools = jedis.zrangeWithScores(key, 0, -1);
            if (schools == null || schools.size() == 0) {     //若redis中没有数据则查询数据库并添加到redis
                LambdaQueryWrapper<School> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(School::getCityId, cid);
                queryWrapper.orderByAsc(School::getId);
                list = schoolService.list(queryWrapper);
                for (School school : list) {
                    //保存入redis中
                    //保存序列化信息
                    String school_str = JsonUtils.object2Json(school);
                    jedis.zadd(key, school.getId(), school_str);
                }
            } else {    //若存在数据则从内存中读取
                list = new ArrayList<>();
                for (Tuple tuple : schools) {
                    //对象的Json串
                    String element = tuple.getElement();
                    //反序列化为对象
                    School school = JsonUtils.json2Object(element, School.class);
                    list.add(school);
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

    /**
     * 新增学校信息
     *
     * @param school
     */
    @Override
    public void saveWithRedis(School school) {
        Jedis jedis = jedisUtils.getJedis();
        String key = "school_" + school.getCityId();
        try {
            schoolService.save(school);
            String school_str = JsonUtils.object2Json(school);
            jedis.zadd(key, school.getId(), school_str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisUtils.close(jedis);
        }
    }
}
