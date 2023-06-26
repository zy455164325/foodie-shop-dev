package com.imooc.service.impl;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.service.IItemsService;
import com.imooc.service.IOrderService;
import com.imooc.service.IUserAddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName OrderServiceImpl
 * @Description 订单接口实现类
 * @Author braveZeng
 * @Date 17:28 2022/11/24
 * Version 1.0
 **/
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private Sid sid;

    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private IItemsService itemsService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String createOrder(SubmitOrderBO orderBO) {
        String userId=orderBO.getUserId();
        String itemSpecIds=orderBO.getItemSpecIds();
        String addressId=orderBO.getAddressId();
        Integer payMethod=orderBO.getPayMethod();
        String leftMsg=orderBO.getLeftMsg();
        //包邮费用设置为0
        Integer postAmount=0;
        //用户地址信息
        UserAddress address=userAddressService.queryUserAddress(userId,addressId);
        //1.新订单数据保存
        Orders newOrder=new Orders();
        String orderId=sid.nextShort();

        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverAddress(address.getProvince()+" "
                                    +address.getCity()+" "
                                    +address.getDistrict()+" "
                                    +address.getDetail());
        newOrder.setReceiverMobile(address.getMobile());
//        newOrder.setTotalAmount();
//        newOrder.setRealPayAmount();
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());
         //2.循环根据itemSpectIds保存订单商品信息表
        String itemSpectArr[]=itemSpecIds.split(",");
        Integer totalAmount =0;//商品原价累计
        Integer realPayAmount =0;//优惠后的实际支付价格累计
        for (String itemSpectId:itemSpectArr){
            //TODO 整合redis后，商品购买的数量重新从redis的购物车获取
            int buyCounts= 1;
            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemsSpec=itemsService.queryItemsSpec(itemSpectId);
            totalAmount+=itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount+=itemsSpec.getPriceDiscount() * buyCounts;

            //2.2 根据规格id获得商品信息以及商品图片
            String itemId=itemsSpec.getItemId();
            Items item=itemsService.getItemById(itemId);
            String imgUrl=itemsService.queryItemMainImgById(itemId);

            //2.3 循环保存子订单数据到数据库
            String subOrderId=sid.nextShort();
            OrderItems subOrderItem=new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpectId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);

            //2.4 在用户提交订单以后，规格表中需要扣除库存
            itemsService.decreaseItemSpecStock(itemSpectId,buyCounts);
        }
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);
         //3.保存订单表状态
        OrderStatus waitPayOrderStatus=new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatusMapper.insert(waitPayOrderStatus);
        return orderId;
    }
}
