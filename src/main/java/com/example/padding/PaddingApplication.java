package com.example.padding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @version 1.0
 * @Date 2023/6/28 14:17
 * @Description 引导程序
 * @Author Sxy
 */

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class PaddingApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaddingApplication.class, args);
        log.info("启动成功");
    }
}
