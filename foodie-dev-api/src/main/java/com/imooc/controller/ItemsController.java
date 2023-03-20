package com.imooc.controller;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.service.IItemsService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.CommentLevelCountVO;
import com.imooc.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *@ClassName ItemsController
 *@Description  商品相关接口
 *@Author braveZeng
 *@Date 15:18 2022/11/24
 *Version 1.0
 **/
@Api(value="商品接口",tags = {"商品信息展示得相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController{

    private  final static Logger log = LoggerFactory.getLogger(ItemsController.class);
    @Autowired
    private IItemsService itemsService;


    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMoocJSONResult info(
            @ApiParam(name="itemId",value="商品id" ,required = true)
            @PathVariable String itemId) {

        if(StringUtils.isBlank(itemId)){
            return IMoocJSONResult.errorMsg(null);
        }
        Items item=itemsService.getItemById(itemId);
        List<ItemsImg> itemImgList=itemsService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList=itemsService.queryItemSpecList(itemId);
        ItemsParam itemsParam=itemsService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO=new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);
        return IMoocJSONResult.ok(itemInfoVO);
    }



    @ApiOperation(value = "查询商品评价等级",notes = "查询商品评价等级",httpMethod = "GET")
    @GetMapping("/commentLevel")
    public IMoocJSONResult commentLevel(
            @ApiParam(name="itemId",value="商品id" ,required = true)
            @RequestParam String itemId) {
        if(StringUtils.isBlank(itemId)){
            return IMoocJSONResult.errorMsg(null);
        }
        CommentLevelCountVO levelCountVO=itemsService.queryCommentCounts(itemId);
        return IMoocJSONResult.ok(levelCountVO);
    }


    @ApiOperation(value = "查询商品评价",notes = "查询商品评价",httpMethod = "GET")
    @GetMapping("/comments")
    public IMoocJSONResult comments(
            @ApiParam(name="itemId",value="商品id" ,required = true)
            @RequestParam String itemId,
            @ApiParam(name="level",value="商品评价等级" ,required = false)
            @RequestParam String level,
            @ApiParam(name="page",value="查询下一页的第几页" ,required = false)
            @RequestParam Integer page,
            @ApiParam(name="pageSize",value="分页的每一页显示的记录数" ,required = false)
            @RequestParam Integer pageSize) {
        if(StringUtils.isBlank(itemId)){
            return IMoocJSONResult.errorMsg(null);
        }

        if(page == null){
            page = 1;
        }

        if(pageSize == null){
            pageSize= COMMENT_PAGE_SIZE;
        }
        PagedGridResult grid=itemsService.queryPagedComments(itemId,level,page,pageSize);
        return IMoocJSONResult.ok(grid);
    }






    @ApiOperation(value = "搜索商品列表",notes = "搜索商品列表",httpMethod = "GET")
    @GetMapping("/search")
    public IMoocJSONResult search(
            @ApiParam(name="keywords",value="关键字" ,required = true)
            @RequestParam String keywords,
            @ApiParam(name="sort",value="排序" ,required = false)
            @RequestParam String sort,
            @ApiParam(name="page",value="查询下一页的第几页" ,required = false)
            @RequestParam Integer page,
            @ApiParam(name="pageSize",value="分页的每一页显示的记录数" ,required = false)
            @RequestParam Integer pageSize) {
        if(StringUtils.isBlank(keywords)){
            return IMoocJSONResult.errorMsg(null);
        }

        if(page == null){
            page = 1;
        }

        if(pageSize == null){
            pageSize= PAGE_SIZE;
        }
        PagedGridResult grid=itemsService.searchPagedItems(keywords,sort,page,pageSize);
        return IMoocJSONResult.ok(grid);
    }


    @ApiOperation(value = "通过分类id搜索商品列表",notes = "通过分类id搜索商品列表",httpMethod = "GET")
    @GetMapping("/catItems")
    public IMoocJSONResult searchItemsByThirdCat(
            @ApiParam(name="catId",value="三级分类id" ,required = true)
            @RequestParam String catId,
            @ApiParam(name="sort",value="排序" ,required = false)
            @RequestParam String sort,
            @ApiParam(name="page",value="查询下一页的第几页" ,required = false)
            @RequestParam Integer page,
            @ApiParam(name="pageSize",value="分页的每一页显示的记录数" ,required = false)
            @RequestParam Integer pageSize) {
        if(StringUtils.isBlank(catId)){
            return IMoocJSONResult.errorMsg(null);
        }

        if(page == null){
            page = 1;
        }

        if(pageSize == null){
            pageSize= PAGE_SIZE;
        }
        PagedGridResult grid=itemsService.searchItemsByThirdCat(catId,sort,page,pageSize);
        return IMoocJSONResult.ok(grid);
    }

}
