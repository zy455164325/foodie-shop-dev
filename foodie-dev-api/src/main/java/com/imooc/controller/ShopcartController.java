package com.imooc.controller;

import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车接口controller",tags = {"购物车接口相关api"})
@RequestMapping("shopcart")
@RestController
public class ShopcartController {
    final private static Logger logger = LoggerFactory.getLogger(ShopcartController.class);


    @ApiOperation(value = "添加商品到购物车",notes = "添加商品到购物车",httpMethod = "POST")
    @PostMapping("/add")
    public IMoocJSONResult add(
        @RequestParam String userId,
        @RequestBody ShopcartBO shopcartBO,
        HttpServletRequest request,
        HttpServletResponse response) {
            if(StringUtils.isBlank(userId)){
                return IMoocJSONResult.errorMsg("");
            }
            //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
            logger.info("shopcartBO:{}",shopcartBO);
            return IMoocJSONResult.ok(shopcartBO);
    }


    @ApiOperation(value = "删除购物车中的商品",notes = "删除购物车中的商品",httpMethod = "POST")
    @PostMapping("/del")
    public IMoocJSONResult  del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)){
            return IMoocJSONResult.errorMsg("参数不能为空");
        }
        //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存

        return IMoocJSONResult.ok();
    }

}
