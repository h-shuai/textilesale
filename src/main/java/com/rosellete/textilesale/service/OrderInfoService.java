package com.rosellete.textilesale.service;

import com.alibaba.fastjson.JSONObject;
import com.rosellete.textilesale.dao.OrderInfoDao;
import com.rosellete.textilesale.model.OrderInfo;
import com.rosellete.textilesale.vo.OrderInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<OrderInfoVO> getWaitPackOrderList(OrderInfo orderInfo){
        List<Map<String,Object>> maps = orderInfoDao.getWaitPackOrderList(orderInfo.getOrderNo(),orderInfo.getCustomerName());
        List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
        for (Map<String,Object> map : maps){
            OrderInfoVO orderInfoVO = JSONObject.parseObject(JSONObject.toJSONString(map),OrderInfoVO.class);
            orderInfoVOList.add(orderInfoVO);
        }
        return orderInfoVOList;
    }
}
