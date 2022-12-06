package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.service.ICarouselService;
import com.imooc.service.ICategoryService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.vo.CategoryVO;
import com.imooc.vo.NewItemsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *@ClassName StuFooController
 *@Description  首页展示相关接口
 *@Author braveZeng
 *@Date 15:18 2022/11/24
 *Version 1.0
 **/
@Api(value="首页",tags = {"首页展示相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    final private static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ICarouselService carouselService;

    @Autowired
    private ICategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "POST")
    @GetMapping("/carousel")
    public IMoocJSONResult carousel() {
        List<Carousel> result=carouselService.queryAll(YesOrNo.YES.type);
        return IMoocJSONResult.ok(result);
    }


    @ApiOperation(value = "获取所有商品分类(一级分类)",notes = "获取所有商品分类(一级分类)",httpMethod = "GET")
    @GetMapping("/cats")
    public IMoocJSONResult cats() {
        List<Category> result=categoryService.queryAllRootLevelCat();
        return IMoocJSONResult.ok(result);
    }

    @ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMoocJSONResult subCat(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable(name = "rootCatId") Integer rootCatId) {

        if(rootCatId == null){
            IMoocJSONResult.errorMsg("分类不存在");
        }

        List<CategoryVO> list=categoryService.getSubCatList(rootCatId);
        return IMoocJSONResult.ok(list);
    }


    @ApiOperation(value = "查询每个一级分类最新6条最新商品数据",notes = "查询每个一级分类最新6条最新商品数据",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMoocJSONResult sixNewItems(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId) {

        if(rootCatId == null){
            IMoocJSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVO> newItemsList=categoryService.getSixNewItemsLazy(rootCatId);
        return IMoocJSONResult.ok(newItemsList);
    }

}
