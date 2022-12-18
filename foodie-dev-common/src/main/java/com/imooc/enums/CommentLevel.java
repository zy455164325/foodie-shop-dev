package com.imooc.enums;

/**
 * @ClassName CommentLevel
 * @Description 商品评级等级 枚举
 * @Author braveZeng
 * @Date 17:54 2022/11/28
 * Version 1.0
 **/
public enum CommentLevel {
    GOOD(1,"好评"),
    NORMAL(2,"中评"),
    BAD(3,"差评");

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public final Integer type;
    public final String value;



}

