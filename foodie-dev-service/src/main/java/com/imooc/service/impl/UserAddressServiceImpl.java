package com.imooc.service.impl;

import com.imooc.enums.YesOrNo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.UserAddressBO;
import com.imooc.service.IUserAddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName AddressServiceImpl
 * @Description 用户地址服务接口实现类
 * @Author braveZeng
 * @Date 17:28 2022/11/24
 * Version 1.0
 **/
@Service
public class UserAddressServiceImpl implements IUserAddressService {
    @Autowired
    private UserAddressMapper addressMapper;
    @Autowired
    private Sid sid;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAddressAll(String userId) {
        UserAddress userAddress=new UserAddress();
        userAddress.setUserId(userId);
        return addressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveNewAddress(UserAddressBO userAddressBO) {
        //1，判断当前用户是否存在地址，如果没有，则新增地址为 ‘默认地址'
        Integer isDefault=0;
        List<UserAddress> userAddressList=this.queryAddressAll(userAddressBO.getUserId());
        if(null==userAddressList || userAddressList.isEmpty() || userAddressList.size()==0){
            isDefault= 1;
        }
        String addressId=sid.nextShort();

        //2. 保存地址到数据库
        UserAddress newUserAddress=new UserAddress();
        BeanUtils.copyProperties(userAddressBO,newUserAddress);
        newUserAddress.setId(addressId);
        newUserAddress.setIsDefault(isDefault);
        newUserAddress.setCreatedTime(new Date());
        newUserAddress.setUpdatedTime(new Date());
        addressMapper.insert(newUserAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(UserAddressBO userAddressBO) {
        String id=userAddressBO.getAddressId();
        UserAddress userAddress=new UserAddress();
        BeanUtils.copyProperties(userAddressBO,userAddress);
        userAddress.setId(id);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        addressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress userAddress=new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        addressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        //1.查找用户默认地址，设置为不默认
        UserAddress queryUserAddress=new UserAddress();
        queryUserAddress.setUserId(userId);
        queryUserAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> list =addressMapper.select(queryUserAddress);
        for (UserAddress ua :list) {
            ua.setIsDefault(YesOrNo.NO.type);
            addressMapper.updateByPrimaryKeySelective(ua);
        }
        //2.根据地址id设置默认地址
        UserAddress defaultUserAddress=new UserAddress();
        defaultUserAddress.setId(addressId);
        defaultUserAddress.setUserId(userId);
        defaultUserAddress.setIsDefault(YesOrNo.YES.type);
        addressMapper.updateByPrimaryKeySelective(defaultUserAddress);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String address) {
        UserAddress singleAddress=new UserAddress();
        singleAddress.setUserId(userId);
        singleAddress.setId(address);
        return addressMapper.selectOne(singleAddress);
    }
}
