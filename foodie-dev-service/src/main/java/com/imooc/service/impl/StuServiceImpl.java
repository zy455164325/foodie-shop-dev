package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName StuServiceImpl
 * @Description 学生服务业务接口实现类
 * @Author braveZeng
 * @Date 17:28 2022/11/24
 * Version 1.0
 **/
@Service
public class StuServiceImpl implements IStuService {
    @Autowired
    @SuppressWarnings("all")
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStu(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveStu() {
    }

    @Override
    public void updateStu(int id) {

    }

    @Override
    public void deleteStu(int id) {
        stuMapper.deleteByPrimaryKey(id);
    }
}
