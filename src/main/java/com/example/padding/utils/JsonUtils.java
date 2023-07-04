package com.example.padding.utils;

import com.alibaba.fastjson.JSON;

/**
 * @version 1.0
 * @Date 2023/7/4 09:17
 * @Description Json工具类，实现序列化与反序列化操作
 * @Author Sxy
 */

public class JsonUtils {
    /**
     * 对象序列化为json
     *
     * @param o
     * @return
     */
    public static String object2Json(Object o) {
        return JSON.toJSONString(o);
    }

    /**
     * json反序列化为对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
