package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.OrderDetailInfoDao;
import com.rosellete.textilesale.model.OrderDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailInfoService {
    @Autowired
    private OrderDetailInfoDao orderDetailInfoDao;

    public List<OrderDetailInfo> findOrderDetailInfoByOrderNoAndProductType(String orderNo, String productType) {
        return orderDetailInfoDao.findAllByOrderNoAndProductType(orderNo, productType);
    }

    public List<OrderDetailInfo> findOrderDetailInfoByOrderNo(String orderNo) {
        return orderDetailInfoDao.findAllByOrderNo(orderNo);
    }

    public List<Map<String, Object>> findOrderDetailInfo(OrderDetailInfo orderDetailInfo, String customerName,
                                                         String deliveryMode, String consignmentDepartment,
                                                         Date startDate, Date endDate) {
        return orderDetailInfoDao.findByCustomerInfoAndDate(orderDetailInfo.getOrderNo(), orderDetailInfo.getProductType(),
                customerName, deliveryMode, consignmentDepartment, startDate, endDate);
    }

    public long findOrderDetailInfoListSize(OrderDetailInfo orderDetailInfo, String customerName,
                                            String deliveryMode, String consignmentDepartment,
                                            Date startDate, Date endDate) {
        return orderDetailInfoDao.findByCustomerInfoAndDate(orderDetailInfo.getOrderNo(), orderDetailInfo.getProductType(),
                customerName, deliveryMode, consignmentDepartment, startDate, endDate).size();
    }

    public List<Map<String, Object>> findOrderDetailPageInfo(OrderDetailInfo orderDetailInfo, String customerName,
                                                             String deliveryMode, String consignmentDepartment,
                                                             Date startDate, Date endDate) {
        return orderDetailInfoDao.findPageByCustomerInfoAndDate(orderDetailInfo.getOrderNo(), orderDetailInfo.getProductType(),
                customerName, deliveryMode, consignmentDepartment, startDate, endDate,
                (orderDetailInfo.getPageNum() - 1) * orderDetailInfo.getPageSize(), orderDetailInfo.getPageSize());
    }

    public void saveOrderDetailInfo(List<OrderDetailInfo> orderDetailInfoList) {
        orderDetailInfoDao.saveAll(orderDetailInfoList);
    }
}
