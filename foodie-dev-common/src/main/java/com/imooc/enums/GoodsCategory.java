package com.imooc.enums;

/**
 * @ClassName GoodsCategory
 * @Description 商品分类 枚举
 * @Author braveZeng
 * @Date 17:54 2022/11/28
 * Version 1.0
 **/
public enum GoodsCategory {
    ONE_GRADE(1,"商品一级分类"),
    TWO_GRADE(2,"商品二级分类"),
    THREE_GRADE(3,"商品三级分类");

    GoodsCategory(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public final Integer type;
    public final String value;



}

