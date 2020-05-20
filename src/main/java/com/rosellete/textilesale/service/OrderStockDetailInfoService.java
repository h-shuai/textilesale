package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.OrderStockDetailInfoDao;
import com.rosellete.textilesale.model.OrderStockDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderStockDetailInfoService {
    @Autowired

    private OrderStockDetailInfoDao orderStockDetailInfoDao;

    public List<OrderStockDetail> findOrderStockDetailInfo(String orderNo, String productType) {
        return orderStockDetailInfoDao.findAllByOrderNoAndProductType(orderNo, productType);
    }

    public void deleteOrderStockDetail(List<OrderStockDetail> orderStockList) {
        orderStockDetailInfoDao.deleteAll(orderStockList);
    }

    public void saveOrderStockDetail(List<OrderStockDetail> orderStockDetailInfoList) {
        orderStockDetailInfoDao.saveAll(orderStockDetailInfoList);
    }

    public List<Map<String, Object>> getPieceList(String orderNo, String productType) {
        return orderStockDetailInfoDao.getPieceList(orderNo, productType);
    }

    public void updateStatusById(String status, String id) {
        orderStockDetailInfoDao.updateStatusById(status, id);
    }

    public OrderStockDetail getStockDetailById(String id) {
        return orderStockDetailInfoDao.getStockDetailById(id);
    }

    public List<OrderStockDetail> findAllByOrderNo(String orderNo) {
        OrderStockDetail info = new OrderStockDetail();
        info.setOrderNo(orderNo);
        Example<OrderStockDetail> example = Example.of(info);
        return orderStockDetailInfoDao.findAll(example);
    }
}
