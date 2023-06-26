package com.imooc.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于添加商品的购物车VO
 */
@Getter
@Setter
public class ShopcartVO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
    }
