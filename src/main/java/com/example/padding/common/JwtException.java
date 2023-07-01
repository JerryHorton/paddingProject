package com.example.padding.common;

/**
 * @version 1.0
 * @Date 2023/7/1 11:39
 * @Description jwt异常
 * @Author Sxy
 */

public class JwtException extends RuntimeException {
    public JwtException() {
        super();
    }

    public JwtException(String msg) {
        super(msg);
    }
}
