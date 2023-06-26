package com.imooc.service;


import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.CommentLevelCountVO;
import com.imooc.vo.ShopcartVO;

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
    Items getItemById(String itemId);

    /**
     * @Author braveZeng
     * @Description 根据商品id查询商品图片列表
     * @Date 11:09 2022/12/12
     * @Param 商品id
     * @return 商品图片列表
     **/
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * @Author braveZeng
     * @Description 根据商品id查询商品规格
     * @Date 11:10 2022/12/12
     * @Param 商品id
     * @return 商品规格列表
     **/
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * @Author braveZeng
     * @Description 根据商品id查询商品参数信息
     * @Date 11:12 2022/12/12
     * @Param  商品id
     * @return  商品参数信息
     **/
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的评级等级数量
     * @param itemId
     * @return
     */
     CommentLevelCountVO queryCommentCounts(String itemId);


    /**
     * 根据商品编号和商品评价等级获取商品评价信息(分页)
     * @param itemId
     * @param level
     * @return 商品评价信息
     */
     PagedGridResult queryPagedComments(String itemId, String level, Integer page, Integer pageSize);

    /**
     * 根据关键字搜索商品列表
     * @param keywords 搜索内容
     * @param sort 排序
     * @param page
     * @param pageSize
     * @return
     */
     PagedGridResult searchPagedItems  (String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据商品三级分类搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItemsByThirdCat(String catId, String sort, Integer page, Integer pageSize);


    /**
     * 根据规格ids查询最新的购物车中商品数据
     * @param specIds
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格id获取规格对象的具体信息
     * @param specId
     * @return
     */
    ItemsSpec queryItemsSpec(String specId);

    /**
     * 根据商品id获得商品图片主图url
     * @param itemId
     * @return
     */
    String queryItemMainImgById(String itemId);


    /**
     * 减少库存
     * @param specId
     * @param buyCounts
     */
    void decreaseItemSpecStock(String specId,int buyCounts);
}
