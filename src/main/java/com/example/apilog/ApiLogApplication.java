package com.example.apilog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// 添加exclude = {DataSourceAutoConfiguration.class}使springboot不检查数据库配置
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApiLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLogApplication.class, args);
    }

}
