package com.imooc.enums;

/**
 * @ClassName PayMethod
 * @Description 支付方式 枚举
 * @Author braveZeng
 * @Date 17:54 2022/11/28
 * Version 1.0
 **/
public enum PayMethod {
    WEIXIN(1,"微信"),
    ALIPAY(2,"支付宝");

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public final Integer type;
    public final String value;



}

