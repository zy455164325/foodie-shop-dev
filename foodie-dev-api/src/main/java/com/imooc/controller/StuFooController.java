package com.imooc.controller;

import com.imooc.pojo.Stu;
import com.imooc.service.IStuService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 *@ClassName StuFooController
 *@Description 第一个restFul控制器类示例
 *@Author braveZeng
 *@Date 15:18 2022/11/24
 *Version 1.0
 **/
@ApiIgnore
@RestController
public class StuFooController {
    @Autowired
    private IStuService stuService;

    @GetMapping("/getStu")
    public Object getStu(int id) {
        return stuService.getStu(id);
    }

    @PostMapping("/saveStu")
    public Object saveStu() {
        Stu stu=new Stu();
        stu.setName("braveZeng");
        stu.setAge(27);
        stuService.saveStu(stu);
        return "200";
    }

    @PutMapping("/updateStu")
    public Object updateStu(int id) {
        stuService.updateStu(id);
        return "200";
    }
    @DeleteMapping("/deleteStu")
    public Object deleteStu(int id) {
        stuService.deleteStu(id);
        return "200";
    }


}
