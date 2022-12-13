package com.imooc.service;


import com.imooc.pojo.*;

import java.util.List;

/**
 * 商品服务接口
 */
public interface IItemsService {

    /**
     * @Author braveZeng
     * @Description 根据商品id查询商品信息
     * @Date 11:07 2022/12/12
     * @Param  商品id
     * @return Items 商品信息
     **/
    public Items getItemById(String itemId);

    /**
     * @Author braveZeng
     * @Description 根据商品id查询商品图片列表
     * @Date 11:09 2022/12/12
     * @Param 商品id
     * @return 商品图片列表
     **/
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * @Author braveZeng
     * @Description 根据商品id查询商品规格
     * @Date 11:10 2022/12/12
     * @Param 商品id
     * @return 商品规格列表
     **/
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * @Author braveZeng
     * @Description 根据商品id查询商品参数信息
     * @Date 11:12 2022/12/12
     * @Param  商品id
     * @return  商品参数信息
     **/
    public ItemsParam queryItemParam(String itemId);
}
