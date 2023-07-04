package com.example.padding.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @version 1.0
 * @Date 2023/7/3 16:22
 * @Description
 * @Author Sxy
 */

@Component
public class JedisUtils {
    @Autowired
    private JedisPool jedisPool;

    /*获取Jedis资源*/
    public  Jedis getJedis() {
        return jedisPool.getResource();
    }
    /*释放Jedis连接*/
    public void close(Jedis jedis) {
        if(jedis!=null) {
            jedis.close();
        }
    }
}
