package com.app;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(value = "com.app.mapper")
public class AppDataSeaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDataSeaApplication.class, args);
    }

}
