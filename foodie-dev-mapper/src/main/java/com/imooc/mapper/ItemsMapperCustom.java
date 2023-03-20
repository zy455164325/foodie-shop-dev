package com.imooc.mapper;

import com.imooc.vo.ItemCommentVO;
import com.imooc.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String,Object> map);


    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String,Object> map);


    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String,Object> map);
}