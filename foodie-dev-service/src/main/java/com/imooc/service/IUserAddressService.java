package com.imooc.service;


import com.imooc.pojo.*;
import com.imooc.pojo.bo.UserAddressBO;

import java.util.List;

/**
 * 用户地址服务接口
 */
public interface IUserAddressService {


    /**
     * 根据用户id获取用户地址
     * @param userId
     * @return
     */
    List<UserAddress> queryAddressAll(String userId);


    /**
     * 用户新增地址
     * @param userAddressBO
     * @return
     */
    void saveNewAddress(UserAddressBO userAddressBO);

    /**
     * 用户修改地址
     * @param userAddressBO
     */
    void updateUserAddress(UserAddressBO userAddressBO);

    /**
     * 用户删除地址
     * @param userId
     * @param addressId
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 用户设置默认地址
     * @param userId
     * @param addressId
     */
    void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id查询用户具体的用户地址对象信息
     * @param userId
     * @param address
     * @return
     */
    UserAddress queryUserAddress(String userId,String address);
}
