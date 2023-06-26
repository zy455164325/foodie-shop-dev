package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.service.IItemsService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.CommentLevelCountVO;
import com.imooc.vo.ItemCommentVO;
import com.imooc.vo.SearchItemsVO;
import com.imooc.vo.ShopcartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @ClassName StuServiceImpl
 * @Description 商品服务业务接口实现类
 * @Author braveZeng
 * @Date 17:28 2022/11/24
 * Version 1.0
 **/
@Service
public class ItemsServiceImpl implements IItemsService {
    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items getItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example itemsImgExample=new Example(ItemsImg.class);
        Example.Criteria criteria=itemsImgExample.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        List<ItemsImg> itemsImgList=itemsImgMapper.selectByExample(itemsImgExample);
        return itemsImgList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example itemsSpecExample=new Example(ItemsSpec.class);
        Example.Criteria criteria=itemsSpecExample.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        List<ItemsSpec> itemsSpecList=itemsSpecMapper.selectByExample(itemsSpecExample);
        return itemsSpecList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example itemsParamExample=new Example(ItemsParam.class);
        Example.Criteria criteria=itemsParamExample.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        ItemsParam itemsParam=itemsParamMapper.selectOneByExample(itemsParamExample);
        return itemsParam;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountVO queryCommentCounts(String itemId) {
        Integer goodCounts=getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts=getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts=getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts=goodCounts + normalCounts + badCounts;

        CommentLevelCountVO  countVO=new CommentLevelCountVO();
        countVO.setTotalCounts(totalCounts);
        countVO.setGoodCounts(goodCounts);
        countVO.setNormalCounts(normalCounts);
        countVO.setBadCounts(badCounts);
        return countVO;
    }




    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId,Integer level){
        ItemsComments condition=new ItemsComments();
        condition.setItemId(itemId);
        if(level !=null){
            condition.setCommentLevel(level);
        }
        return  itemsCommentsMapper.selectCount(condition);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId,
                                                  String level,
                                                  Integer page,
                                                  Integer pageSize) {
        Map<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("itemId",itemId);
        paramsMap.put("level",level);

        /**
         * page:第几页
         * pageSize:每页显示条数
         */
        PageHelper.startPage(page,pageSize);

        List<ItemCommentVO> list=itemsMapperCustom.queryItemComments(paramsMap);

        for (ItemCommentVO vo: list) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        return setterPagedGrid(list,page);
    }

    @Override
    public PagedGridResult searchPagedItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("keywords",keywords);
        paramsMap.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list=itemsMapperCustom.searchItems(paramsMap);
        return setterPagedGrid(list,page);

    }

    @Override
    public PagedGridResult searchItemsByThirdCat(String catId, String sort, Integer page, Integer pageSize) {
        Map<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("catId",catId);
        paramsMap.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list=itemsMapperCustom.searchItemsByThirdCat(paramsMap);
        return setterPagedGrid(list,page);
    }


    private PagedGridResult setterPagedGrid(List<?> list, Integer page){
        PageInfo<?> pageList =new PageInfo<>(list);
        PagedGridResult grid=new PagedGridResult(page,pageList.getPages(),
                                                pageList.getTotal(),list);
        return grid;
    }


    /**
     * 根据规格ids查询最新的购物车中商品数据
     *
     * @param specIds
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {
        String[] ids =specIds.split(",");
        List<Object> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList,ids);

        return  itemsMapperCustom.queryItemsBySpecIds(specIdsList);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsSpec queryItemsSpec(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg=new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result= itemsImgMapper.selectOne(itemsImg);
        return result != null ?result.getUrl() : null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void decreaseItemSpecStock(String specId, int buyCounts) {
        //这里会涉及多线程安全的问题
        //synchronized 不推荐使用，集群下无用，性能低下
        //锁数据库： 不推荐，导致数据库性能低下
        //分布式锁 zookeeper redis
        int result = itemsMapperCustom.decreaseItemSpecStock(specId,buyCounts);
        if(result!=1){
            throw new RuntimeException("订单创建失败：订单库存不足！");
        }
    }

}
