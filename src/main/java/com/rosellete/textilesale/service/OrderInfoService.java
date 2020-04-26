package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.OrderInfoDao;
import com.rosellete.textilesale.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoDao orderInfoDao;


    public OrderInfo getOrderInfo(String orderNo) {
        Optional<OrderInfo> orderInfo = orderInfoDao.findById(orderNo);
        return orderInfo.isPresent() ? orderInfo.get() : null;
    }

    public List<OrderInfo> getOrderListByCustomerInfo(OrderInfo orderInfo) {
        return orderInfoDao.getOrderList(orderInfo.getOrderNo(), orderInfo.getCustomerName(), orderInfo.getCustomerPhoneNo(), orderInfo.getOrderStatus());
    }

    public List<OrderInfo> getOrderListByCustomerInfoAndDate(OrderInfo orderInfo, Date startDate, Date endDate) {
        return orderInfoDao.getOrderList(orderInfo.getOrderNo(), orderInfo.getCustomerName(), orderInfo.getCustomerPhoneNo(), orderInfo.getOrderStatus(), startDate, endDate);
    }

    public void updateOrderStatus(String orderNo, String orderStatus) {
        orderInfoDao.updateOrderStatus(orderNo, orderStatus, "admin");
    }

    public void saveOrderInfo(OrderInfo orderInfo) {
        orderInfoDao.save(orderInfo);
    }

    public void updateOrderInfo(String orderNo,Double sumAmount,String updater) {
        orderInfoDao.updateAmount(orderNo,sumAmount,updater);
    }
}
