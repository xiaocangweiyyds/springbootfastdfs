package com.example.springbootfastdfs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.yr")
@MapperScan(basePackages = "com.yr.mapper")
public class SpringbootfastdfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootfastdfsApplication.class, args);
    }

}
