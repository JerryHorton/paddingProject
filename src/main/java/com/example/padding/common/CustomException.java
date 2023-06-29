package com.example.padding.common;

/**
 * @version 1.0
 * @Date 2023/6/24 14:48
 * @Description 业务异常
 * @Author Sxy
 */

public class CustomException extends RuntimeException {
    public CustomException(String msg) {
        super(msg);
    }
}
