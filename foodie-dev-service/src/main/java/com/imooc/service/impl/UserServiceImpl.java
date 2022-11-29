package com.imooc.service.impl;

import com.imooc.enums.Sex;
import com.imooc.mapper.StuMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Stu;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.IStuService;
import com.imooc.service.IUserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @ClassName StuServiceImpl
 * @Description 用户服务业务接口实现类
 * @Author braveZeng
 * @Date 17:28 2022/11/24
 * Version 1.0
 **/
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    //头像地址
    private static final String USER_FACE="http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";


    @Override
    public boolean queryUsernameIsExist(String username) {
        //创建一个userExample查询条件
        Example userExample=new Example(Users.class);
        userExample.createCriteria().andEqualTo("username",username);
        Users result=usersMapper.selectOneByExample(userExample);
        return result==null? false:true;
    }

    @Transactional(propagation =Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBo) {
        Users user=new Users();
        String userid=sid.nextShort();
        user.setId(userid);
        user.setUsername(userBo.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        }catch (Exception ex){
            ex.getStackTrace();
        }
        // 默认用户昵称为用户名
        user.setNickname(userBo.getUsername());
        // 默认头像
        user.setFace(USER_FACE);
        // 默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 设置性别为保密
        user.setSex(Sex.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        // 保存用户信息
        usersMapper.insert(user);
        return user;
    }
}
