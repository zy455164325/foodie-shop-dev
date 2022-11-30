package com.imooc.enums;

/**
 * @ClassName YesOrNo
 * @Description 是否 枚举
 * @Author braveZeng
 * @Date 17:54 2022/11/28
 * Version 1.0
 **/
public enum YesOrNo {
    NO(0,"否"),
    YES(1,"是");

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public final Integer type;
    public final String value;



}

