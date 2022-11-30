package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.service.ICarouselService;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 *@ClassName StuFooController
 *@Description  首页展示相关接口
 *@Author braveZeng
 *@Date 15:18 2022/11/24
 *Version 1.0
 **/
@Api(value="首页",tags = {"首页展示相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    final private static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ICarouselService carouselService;

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "POST")
    @GetMapping("/carousel")
    public IMoocJSONResult carousel() {
        List<Carousel> result=carouselService.queryAll(YesOrNo.YES.type);
        return IMoocJSONResult.ok(result);
    }




}
