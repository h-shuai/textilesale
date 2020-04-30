package com.rosellete.textilesale.service;

import com.alibaba.fastjson.JSONObject;
import com.rosellete.textilesale.dao.OrderInfoDao;
import com.rosellete.textilesale.model.OrderInfo;
import com.rosellete.textilesale.vo.OrderInfoVO;
import com.rosellete.textilesale.vo.PackInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoDao orderInfoDao;


    public OrderInfo findByPrimaryKey(String orderNo) {
        Optional<OrderInfo> orderInfo = orderInfoDao.findById(orderNo);
        return orderInfo.orElse(null);
    }

    public List<OrderInfo> findOrderListByCustomerInfo(OrderInfo orderInfo, Date startDate, Date endDate) {
        return orderInfoDao.getOrderList(orderInfo.getOrderNo(), orderInfo.getCustomerName(),
                orderInfo.getCustomerPhoneNo(), orderInfo.getOrderStatus(), startDate, endDate);
    }

    public void updateOrderStatus(String orderNo, String orderStatus) {
        orderInfoDao.updateOrderStatus(orderNo, orderStatus, "admin");
    }

    public void saveOrderInfo(OrderInfo orderInfo) {
        orderInfoDao.save(orderInfo);
    }

    public void updateOrderInfo(String orderNo, String orderStatus, Double sumAmount, String updater) {
        orderInfoDao.updateStatusAndAmount(orderNo, orderStatus, sumAmount, updater);
    }

    public List<OrderInfoVO> getWaitPackOrderList(OrderInfo orderInfo) {
        List<Map<String, Object>> maps = orderInfoDao.getWaitPackOrderList(orderInfo.getOrderNo(), orderInfo.getCustomerName());
        List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            OrderInfoVO orderInfoVO = JSONObject.parseObject(JSONObject.toJSONString(map), OrderInfoVO.class);
            orderInfoVOList.add(orderInfoVO);
        }
        return orderInfoVOList;
    }

    public Map<String, Object> getTotalCount(String orderNo) {
        return orderInfoDao.getTotalCount(orderNo);
    }

    public List<PackInfoVO> getWaitPieceList(String orderNo) {
        List<PackInfoVO> returnList = new ArrayList<>();
        List<Map<String, Object>> pieceList = orderInfoDao.getWaitPieceList(orderNo);
        for (Map<String, Object> map : pieceList) {
            PackInfoVO packInfoVO = new PackInfoVO();
            packInfoVO.setPicurl((String) map.get("picurl"));
            packInfoVO.setColthModel((String) map.get("colthModel"));
            packInfoVO.setCheckSelected(new String[0]);
            returnList.add(packInfoVO);
        }
        return returnList;
    }
}
