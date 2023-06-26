package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.IUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public IMoocJSONResult regist(@RequestBody UserBO userBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
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
        Users userResult=userService.createUser(userBO);

        //对用户信息脱敏
        userResult=setNullProperty(userResult);

        //设置用户cookie信息，isEncode 为true设置cookie信息加密
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);

        //TODO 生成用户token,存入redis会话
        //TODO 同步购物车数据
        return IMoocJSONResult.ok();
    }

    /**
     * @Author braveZeng
     * @Description 用户登录
     * @Date 16:02 2022/11/29
     * @Param
     * @return
     **/
    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public IMoocJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception{
        String username=userBO.getUsername();
        String password=userBO.getPassword();

        //0. 判断用户名称和密码不能为空
        if(StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)){
            return IMoocJSONResult.errorMsg("用户名密码不能为空");
        }

        // 1.实现登录
        Users userResult=userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if(userResult == null){
            return IMoocJSONResult.errorMsg("用户名或密码不正确");
        }
        //对用户信息脱敏
        userResult=setNullProperty(userResult);

        //设置用户cookie信息，isEncode 为)true设置cookie信息加密
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);

        //TODO 生成用户token,存入redis会话
        //TODO 同步购物车数据

        return IMoocJSONResult.ok(userResult);
    }


    private Users  setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
    /**
     * @Author braveZeng
     * @Description 用户退出登录
     * @Date 15:32 2022/11/30
     **/
    @ApiOperation(value = "用户退出登录",notes = "用户退出登录",httpMethod = "POST")
    @PostMapping("/logout")
    public IMoocJSONResult logout(@RequestParam(name ="userId") String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        //清楚用户相关信息的cookie
        CookieUtils.deleteCookie(request,response,"user");
        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据
        return IMoocJSONResult.ok();
    }
}
