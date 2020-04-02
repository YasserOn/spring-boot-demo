package com.xkcoding.netty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.xkcoding.netty.mapper"})
public class SpringBootDemoNetty {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoNetty.class);
    }
}
