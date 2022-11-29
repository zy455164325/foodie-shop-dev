package com.imooc.service;


import com.imooc.pojo.Stu;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

public interface IUserService {
    /**
     * @Author braveZeng
     * @Description 判断用户名是否存在
     * @Date 18:07 2022/11/24
     * @Param username :用户名称
     * @return Boolean: true:存在 false：不存在
     **/
    public boolean queryUsernameIsExist(String username);

    /**
     * @Author braveZeng
     * @Description 创建一个新用户
     * @Date 17:29 2022/11/28
     * @Param  前端请求的用户信息
     * @return 用户信息
     **/
    public Users createUser(UserBO userBo);


    /**
     * @Author braveZeng
     * @Description 检查用户名和密码是否匹配
     * @Date 17:29 2022/11/29
     * @Param  username:用户名，password：密码
     * @return 用户信息
     **/
    public Users queryUserForLogin(String username,String password);



}
