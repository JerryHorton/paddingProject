package com.example.padding.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @version 1.0
 * @Date 2023/6/23 09:51
 * @Description 全局异常处理
 * @Author Sxy
 */

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     * SQL异常处理方法
     *
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.info(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    /**
     * 业务异常处理方法
     *
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex) {
        log.info(ex.getMessage());
        return R.error(ex.getMessage());
    }

    /**
     * jwt处理方法
     *
     * @return
     */
    @ExceptionHandler(JwtException.class)
    public R<String> exceptionHandler(JwtException ex) {
        log.info(ex.getMessage());
        return R.error(ex.getMessage());
    }
}
