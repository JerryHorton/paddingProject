package com.example.padding.common;

/**
 * @version 1.0
 * @Date 2023/6/24 10:30
 * @Description 基于ThreadLocal封装工具类
 * @Author Sxy
 */

public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
