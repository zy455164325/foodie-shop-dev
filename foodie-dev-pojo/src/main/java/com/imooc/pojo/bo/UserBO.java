package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName UserBO
 * @Description TODO
 * @Author braveZeng
 * @Date 17:33 2022/11/28
 * Version 1.0
 **/
@ApiModel(value = "用户对象BO",description = "从客户端，由客户传入的数据封装在此entity中")
public class UserBO {

    @ApiModelProperty(value = "用户名",name = "username",example = "jack",required = true)
    private String username;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @ApiModelProperty(value = "密码",name = "password",example = "000000",required = true)
    private String password;

    @ApiModelProperty(value = "确认密码",name = "confirmPassword",example = "000000",required = false)
    private String confirmPassword;


}
