package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


/**
 *@ClassName Application
 *@Description App启动类
 *@Author braveZeng
 *@Date 15:18 2022/11/24
 *Version 1.0
 **/
@SpringBootApplication
//扫描 mybatis通用mapper 所在的包
@MapperScan(basePackages = "com.imooc.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
