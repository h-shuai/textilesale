package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.OrderDetailInfoDao;
import com.rosellete.textilesale.model.OrderDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailInfoService {
    @Autowired
    private OrderDetailInfoDao orderDetailInfoDao;

    public List<OrderDetailInfo> getOrderDetailInfoByOrderNoAndProductType(String orderNo,String productType){
        return orderDetailInfoDao.findAllByOrderNoAndProductType(orderNo,productType);
    }

    public List<OrderDetailInfo> getOrderDetailInfoByOrderNo(String orderNo){
        return orderDetailInfoDao.findAllByOrderNo(orderNo);
    }

}
