package com.imooc.controller;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.service.IItemsService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class ItemsController {

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



}
