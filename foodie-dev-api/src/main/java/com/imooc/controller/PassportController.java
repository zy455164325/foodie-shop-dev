package com.imooc.controller;

import com.imooc.pojo.bo.UserBO;
import com.imooc.service.IUserService;
import com.imooc.service.impl.UserServiceImpl;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 *@ClassName StuFooController
 *@Description 通行证控制器类
 *@Author braveZeng
 *@Date 15:18 2022/11/24
 *Version 1.0
 **/
@Api(value="注册登录",tags = {"用于用户注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private IUserService userService;

    /**
     * 查询用户是否存在
     * @param username 用户名称
     * @return
     */
    @ApiOperation(value = "用户是否存在",notes = "用户是否存在",httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMoocJSONResult usernameIsExist(@RequestParam(name ="username") String username) {
        //1.判断用户名称不能为空
        if(StringUtils.isBlank(username)){
            return  IMoocJSONResult.errorMsg("用户名不能为空！");
        }

        //2.判断用户名称是否存在
        boolean isExist=userService.queryUsernameIsExist(username);
        if(isExist){
            return  IMoocJSONResult.errorMsg("用户已经存在！");
        }
        //3.请求成功，用户名没有重复
        return IMoocJSONResult.ok();
    }

    /**
     * 用户注册
     * @param userBO 前端请求的用户json数据对象
     * @return
     */
    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public IMoocJSONResult regist(@RequestBody UserBO userBO) {
        String username=userBO.getUsername();
        String password=userBO.getPassword();
        String confirmPassword=userBO.getConfirmPassword();
        //0. 判断用户名称和密码不能为空
        if(StringUtils.isBlank(username) ||
            StringUtils.isBlank(password) ||
            StringUtils.isBlank(confirmPassword)){
            return IMoocJSONResult.errorMsg("用户名密码不能为空");
        }
        //1. 查询用户是否存在
        boolean isExist=userService.queryUsernameIsExist(username);
        if(isExist){
            return  IMoocJSONResult.errorMsg("用户已经存在！");
        }
        //2. 密码长度不能少于6位
        if(password.length() < 6){
            return  IMoocJSONResult.errorMsg("密码长度不能小于6位！");
        }
        //3. 判断密码和确认密码是否一致
        if(!password.equals(confirmPassword)){
            return  IMoocJSONResult.errorMsg("两次密码输入不一致！");
        }
        //4. 实现注册
        userService.createUser(userBO);

        return IMoocJSONResult.ok();
    }


}
