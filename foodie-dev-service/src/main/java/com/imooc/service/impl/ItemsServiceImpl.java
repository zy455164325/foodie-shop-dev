package com.imooc.service.impl;

import com.imooc.mapper.ItemsImgMapper;
import com.imooc.mapper.ItemsMapper;
import com.imooc.mapper.ItemsParamMapper;
import com.imooc.mapper.ItemsSpecMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.service.IItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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


}
