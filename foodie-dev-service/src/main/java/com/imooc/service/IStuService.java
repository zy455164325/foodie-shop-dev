package com.imooc.service;


import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import org.springframework.beans.factory.annotation.Autowired;

public interface IStuService {
    /**
     * @Author braveZeng
     * @Description 根据学生id获取学生信息
     * @Date 18:07 2022/11/24
     * @Param id :用户编号
     * @return Stu: 学生信息
     **/
    public Stu getStu(int id);

    public void saveStu();

    public void updateStu(int id);

    public void deleteStu(int id);


}
