package com.imooc.controller;

import com.imooc.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName StuFooController
 *@Description 第一个restFul控制器类示例
 *@Author braveZeng
 *@Date 15:18 2022/11/24
 *Version 1.0
 **/
@RestController
public class StuFooController {
    @Autowired
    private IStuService stuService;

    @GetMapping("/getStu")
    public Object getStu(int id) {
        return stuService.getStu(id);
    }


}
