package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.vo.OrderInfoVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("订单测试用例")
@SpringBootTest
class OrderBusinessTest {

    @Autowired
    private OrderBusiness orderBusiness;

    @AfterEach
    void tearDown() {
        orderBusiness = null;
    }

    @DisplayName("获取订单列表测试")
    @Test
    void getOrderList() {
        Assertions.assertNotNull(orderBusiness, "orderBusiness is null");
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO.setOrderNo("20202504151603979");
        PageInfo<OrderInfoVO> orderList = orderBusiness.getOrderList(orderInfoVO);
        System.out.println("orderList = " + orderList);
        orderList.getList().stream().forEach(e -> System.out.println("OrderNo="+e.getOrderNo() +"\tCustomerName = " + e.getCustomerName()));
    }

    @DisplayName("时间格式化字符串")
    @Test
    void generateOrderNo(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println("LocalDateTime = " + now);
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyyMMddHHmmssS");
        System.out.println("now.format(dateTimeFormatter) = " + now.format(dateTimeFormatter));
        System.out.println("nanoTime = " + System.nanoTime());
        System.out.println("currentTimeMillis = " + System.currentTimeMillis());
        dateTimeFormatter=DateTimeFormatter.ofPattern("yyyyMMddHHmmssn");
        System.out.println("now.format(dateTimeFormatter) = " + now.format(dateTimeFormatter)+"\t"+now.format(dateTimeFormatter).length()+"\t"+now.format(dateTimeFormatter).substring(0,17));

    }

    @DisplayName("空字符串转化")
    @Test
    void objectTrim2Null(){

        OrderInfoVO orderInfoVO =new OrderInfoVO();
        orderInfoVO.setOrderNo("1");
        orderInfoVO.setCustomerName("   ");
        String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderInfoVO);
        Arrays.stream(nullOrBlankPropertyNames).forEach(e-> System.out.println(e));
    }
}