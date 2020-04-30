package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.OrderStockDetailInfoDao;
import com.rosellete.textilesale.model.OrderStockDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderStockDetailInfoService {
    @Autowired

    private OrderStockDetailInfoDao orderStockDetailInfoDao;

    public List<OrderStockDetailInfo> findOrderStockDetailInfo(String orderNo, String productType) {
        return orderStockDetailInfoDao.findAllByOrderNoAndProductType(orderNo, productType);
    }

    public void deleteOrderStockDetail(List<OrderStockDetailInfo> orderStockList ) {
        orderStockDetailInfoDao.deleteAll(orderStockList);
    }

    public void saveOrderStockDetail(List<OrderStockDetailInfo> orderStockDetailInfoList) {
        orderStockDetailInfoDao.saveAll(orderStockDetailInfoList);
    }

    public List<Map<String,Object>> getPieceList(String orderNo, String productType){
        return orderStockDetailInfoDao.getPieceList(orderNo,productType);
    }
}
