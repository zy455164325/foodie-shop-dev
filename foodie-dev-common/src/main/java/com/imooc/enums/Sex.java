package com.imooc.enums;

/**
 * @ClassName Sex
 * @Description 性别 枚举
 * @Author braveZeng
 * @Date 17:54 2022/11/28
 * Version 1.0
 **/
public enum Sex {
    woman(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public final Integer type;
    public final String value;



}

