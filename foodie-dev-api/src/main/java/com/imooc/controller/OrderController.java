package com.imooc.controller;

import com.imooc.enums.PayMethod;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.service.IOrderService;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单相关",tags = {"订单相关接口的api"})
@RequestMapping("orders")
@RestController
public class OrderController extends BaseController{
    final private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;

    @ApiOperation(value = "用户下单",notes = "用户下单",httpMethod = "POST")
    @PostMapping("/create")
    public IMoocJSONResult create(
        @RequestBody SubmitOrderBO submitOrderBO,
        HttpServletRequest request,
        HttpServletResponse response) {
            logger.info("submitOrderBO:{}",submitOrderBO.toString());

            if(!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)
              && !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)){
                return IMoocJSONResult.errorMsg("支付方式不支持");
            }

            //1.创建订单
            String orderId= orderService.createOrder(submitOrderBO);
            //2.创建订单以后，移除购物车中已结算（已提交）的商品
            /**
             * 1001
             * 2002 -> 用户购买
             * 2003 -> 用户购买
             * 4004
             */
            //TODO 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
//            CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);

            //3.向支付中心发送当前订单，用于保存支付中心的订单数据
            return IMoocJSONResult.ok(orderId);
    }



}
