package com.imooc.service;


import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.vo.CategoryVO;
import com.imooc.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    /**
     * @Author braveZeng
     * @Description 查询所有一级分类
     * @Date 17:38 2022/11/30
     * @return  List<Category> 所有商品一级分类信息
     **/
    public List<Category> queryAllRootLevelCat();

    /**
     * @Author braveZeng
     * @Description 根据一级分类查询所属一级分类的二级分类
     * @Date 15:00 2022/12/2
     * @Param  rootCatId 父级分类(一级商品分类)
     * @return List<CategoryVO> 商品二级分类信息
     **/
    public List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * @Author braveZeng
     * @Description 每类商品分类推荐6个最新商品
     * @Date 15:15 2022/12/5
     * @Param rootCatId 商品分类id
     * @return List<NewItemsVO> 分类最新6个商品信息
     **/
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
