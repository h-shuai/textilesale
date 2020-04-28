package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.OrderStockDetailInfoDao;
import com.rosellete.textilesale.model.OrderStockDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStockDetailInfoService {
    @Autowired

    private OrderStockDetailInfoDao orderStockDetailInfoDao;

    public List<OrderStockDetailInfo> findOrderStockDetailInfo(String orderNo, String productType){
        return orderStockDetailInfoDao.findAllByOrderNoAndProductType(orderNo,productType);
    }

    public void deleteOrderStockDetail(String orderNo, String productType) {
        OrderStockDetailInfo orderStockDetailInfo = new OrderStockDetailInfo();
        orderStockDetailInfo.setOrderNo(orderNo);
        orderStockDetailInfo.setProductType(productType);
        orderStockDetailInfoDao.delete(orderStockDetailInfo);
    }

    public void saveOrderStockDetail(List<OrderStockDetailInfo> orderStockDetailInfoList) {
        orderStockDetailInfoDao.saveAll(orderStockDetailInfoList);
    }

    public List<String> getPieceList(String orderNo,String productType){
        return orderStockDetailInfoDao.getPieceList(orderNo,productType);
    }
}
