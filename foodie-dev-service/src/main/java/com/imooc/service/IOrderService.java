package com.imooc.service;


import com.imooc.pojo.bo.SubmitOrderBO;

public interface IOrderService {

    /**
     * 用于创建订单相关信息
     * @param orderBO
     * @return
     */
     String createOrder(SubmitOrderBO orderBO);


}
