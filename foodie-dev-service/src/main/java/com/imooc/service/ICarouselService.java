package com.imooc.service;


import com.imooc.pojo.Carousel;

import java.util.List;

public interface ICarouselService {
    /**
     * @Author braveZeng
     * @Description 查询所有轮播图列表
     * @Date 17:38 2022/11/30
     * @Param isShow 是否显示
     * @return  List<Carousel> 所有轮播图信息
     **/
    public List<Carousel> queryAll(Integer isShow);

}
