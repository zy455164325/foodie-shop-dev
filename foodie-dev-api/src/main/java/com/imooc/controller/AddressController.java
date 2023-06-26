package com.imooc.controller;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.pojo.bo.UserAddressBO;
import com.imooc.service.IUserAddressService;
import com.imooc.service.IUserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "地址相关",tags = {"地址相关的api"})
@RequestMapping("address")
@RestController
public class AddressController {
    final private static Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    IUserAddressService userAddressService;

    /**
     *  用户在确定订单界面，可以针对收货地址做如下操作
     *  1.查询用户所有收货地址列表
     *  2.新增收货地址
     *  3.删除收货地址
     *  4.修改收货地址
     *  5.设置默认地址
     */
    @ApiOperation(value = "根据用户id获取的用户地址",notes = "根据用户id获取的用户地址",httpMethod = "POST")
    @PostMapping("/list")
    public IMoocJSONResult list(
            @ApiParam(name="userId",value="用户编号" ,required = true)
            @RequestParam String userId) {
        if (StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("");
        }
        List<UserAddress> userAddressList=userAddressService.queryAddressAll(userId);
        return IMoocJSONResult.ok(userAddressList);
    }


    @ApiOperation(value = "新增收货地址",notes = "新增收货地址",httpMethod = "POST")
    @PostMapping("/add")
    public IMoocJSONResult add(
        @RequestBody UserAddressBO userAddressBO) {

        //数据校验
        IMoocJSONResult check=checkAddress(userAddressBO);
        if(check.getStatus()!=200){
            return check;
        }
        userAddressService.saveNewAddress(userAddressBO);
        return IMoocJSONResult.ok();
    }

    @ApiOperation(value = "用户修改地址",notes = "用户修改地址",httpMethod = "POST")
    @PostMapping("/update")
    public IMoocJSONResult update(
        @RequestBody UserAddressBO userAddressBO) {
        if(StringUtils.isBlank(userAddressBO.getAddressId()) ){
            return IMoocJSONResult.errorMsg("修改地址错误：addressId不能为空");
        }
        //数据校验
        IMoocJSONResult check=checkAddress(userAddressBO);
        if(check.getStatus()!=200){
            return check;
        }
        userAddressService.updateUserAddress(userAddressBO);
        return IMoocJSONResult.ok();
    }


    @ApiOperation(value = "用户删除地址",notes = "用户删除地址",httpMethod = "POST")
    @PostMapping("/delete")
    public IMoocJSONResult delete(
        @RequestParam String userId,
        @RequestParam String addressId
        ) {
        if(StringUtils.isBlank(userId) ||  StringUtils.isBlank(addressId)){
            return IMoocJSONResult.errorMsg("");
        }
        userAddressService.deleteUserAddress(userId,addressId);
        return IMoocJSONResult.ok();
    }

    @ApiOperation(value = "用户设置默认地址",notes = "用户设置默认地址",httpMethod = "POST")
    @PostMapping("/setDefault")
    public IMoocJSONResult setDefault(
        @RequestParam String userId,
        @RequestParam String addressId
        ) {
        if(StringUtils.isBlank(userId) ||  StringUtils.isBlank(addressId)){
            return IMoocJSONResult.errorMsg("");
        }
        userAddressService.updateUserAddressToBeDefault(userId,addressId);
        return IMoocJSONResult.ok();
    }





    private IMoocJSONResult checkAddress(UserAddressBO addressBO){
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)){
            return IMoocJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12){
            return IMoocJSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)){
            return IMoocJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length()!=11){
            return IMoocJSONResult.errorMsg("收货人手机号长度不正确");
        }

        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk){
            return IMoocJSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province)||
                StringUtils.isBlank(city)||
                StringUtils.isBlank(district)||
                StringUtils.isBlank(detail)){
            return IMoocJSONResult.errorMsg("收货信息不能为空");
        }

        return IMoocJSONResult.ok();
    }

}
