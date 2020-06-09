package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.OrderInfoDao;
import com.rosellete.textilesale.model.OrderInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@DisplayName("订单DAO测试用例")
@SpringBootTest
class OrderInfoServiceTest {
    @Autowired
    private OrderInfoDao orderInfoDao;

    @AfterEach
    void tearDown() {
        orderInfoDao = null;
    }

    @DisplayName("测试排序分页")
    @Test
    void findOrderListByCustomerInfo() {
        Assertions.assertNotNull(orderInfoDao, "orderInfoDao is null");
        List<OrderInfo> pagedOrderList = orderInfoDao.findPagedOrderList(null, null, null,
                null, null, null, null, 0, 10);
        pagedOrderList.parallelStream().forEachOrdered(e -> System.out.println(e.getOrderNo() + "\t" + e.getOrderDate()));
        int size = orderInfoDao.findOrderList(null, null, null,
                null, null, null, null).size();
        System.out.println("size = " + size);
    }
}