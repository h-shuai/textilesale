package com.rosellete.textilesale.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.OrderBusiness;
import com.rosellete.textilesale.model.OrderDetailInfo;
import com.rosellete.textilesale.model.OrderInfo;
import com.rosellete.textilesale.model.OrderStockDetailInfo;
import com.rosellete.textilesale.service.OrderDetailInfoService;
import com.rosellete.textilesale.service.OrderInfoService;
import com.rosellete.textilesale.service.OrderStockDetailInfoService;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("orderBusiness")
@Slf4j
public class OrderBusinessImpl implements OrderBusiness {
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderDetailInfoService orderDetailInfoService;
    @Autowired
    private OrderStockDetailInfoService orderStockDetailInfoService;

    @Override
    public PageInfo<OrderInfoVO> getOrderList(OrderInfoVO orderInfoVO) {
        PageHelper.startPage(orderInfoVO.getPageNum(), orderInfoVO.getPageSize());
        String[] nullPropertyNames = NullPropertiesUtil.getNullPropertyNames(orderInfoVO);
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderInfoVO, orderInfo, nullPropertyNames);
        List<OrderInfo> orderList;
        Date startDate = orderInfoVO.getEndDate(), endDate = orderInfoVO.getStartDate();
        if (!(null == startDate && null == endDate)) {
            if (null != endDate) {
                Calendar calendarInstance = Calendar.getInstance();
                calendarInstance.setTime(endDate);
                calendarInstance.add(Calendar.DATE, 1);
                endDate = calendarInstance.getTime();
            }
            orderList = orderInfoService.getOrderListByCustomerInfoAndDate(orderInfo, startDate, endDate);
        } else {
            orderList = orderInfoService.getOrderListByCustomerInfo(orderInfo);
        }
        List<OrderInfoVO> collect = orderList.stream().map(e -> {
            OrderInfoVO temp = new OrderInfoVO();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public PageInfo<OrderDetailInfoVO> getOrderStockDetailInfo(String orderNo) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderNo);
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.getOrderDetailInfoByOrderNo(orderNo);
        List<OrderDetailInfoVO> parsedList = orderDetailInfoList.stream().map(e -> {
            OrderDetailInfoVO temp = new OrderDetailInfoVO();
            BeanUtils.copyProperties(orderInfo, temp);
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(parsedList);
    }

    @Override
    public PageInfo<OrderStockDetailInfoVO> getOrderStockDetailInfo(String orderNo, String productType) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderNo);
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.getOrderDetailInfoByOrderNoAndProductType(orderNo, productType);
        OrderDetailInfo orderDetailInfo = orderDetailInfoList.stream().findFirst().orElse(null);
        List<OrderStockDetailInfo> orderStockDetailInfoList = orderStockDetailInfoService.getOrderStockDetailInfo(orderNo, productType);
        List<OrderStockDetailInfoVO> parsedList = orderStockDetailInfoList.stream().map(e -> {
            OrderStockDetailInfoVO temp = new OrderStockDetailInfoVO();
            BeanUtils.copyProperties(orderInfo, temp);
            BeanUtils.copyProperties(orderDetailInfo, temp);
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(parsedList);
    }

    @Override
    public void confirmOrderStock(String orderNo) {
        orderInfoService.updateOrderStatus(orderNo, "2");
    }

    @Override
    public void orderRestock(String orderNo) {
        orderInfoService.updateOrderStatus(orderNo, "1");
    }

    @Override
    public PageInfo<OrderDetailInfoVO> getOrderDetailList(OrderDetailInfoVO orderDetailInfoVO) {
        PageHelper.startPage(orderDetailInfoVO.getPageNum(), orderDetailInfoVO.getPageSize());
        String[] nullPropertyNames = NullPropertiesUtil.getNullPropertyNames(orderDetailInfoVO);
        OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
        BeanUtils.copyProperties(orderDetailInfoVO, orderDetailInfo, nullPropertyNames);
        List<Map<String, Object>> resultSetList;
        Date startDate = orderDetailInfoVO.getEndDate(), endDate = orderDetailInfoVO.getStartDate();
        if (!(null == startDate && null == endDate)) {
            if (null != endDate) {
                Calendar calendarInstance = Calendar.getInstance();
                calendarInstance.setTime(endDate);
                calendarInstance.add(Calendar.DATE, 1);
                endDate = calendarInstance.getTime();
            }
            resultSetList = orderDetailInfoService.getOrderDetailInfo(orderDetailInfoVO.getOrderNo(), orderDetailInfoVO.getProductType(), orderDetailInfoVO.getCustomerName(), orderDetailInfoVO.getDeliveryMode(), orderDetailInfoVO.getConsignmentDepartment(), startDate, endDate);
        } else {
            resultSetList = orderDetailInfoService.getOrderDetailInfo(orderDetailInfoVO.getOrderNo(), orderDetailInfoVO.getProductType(), orderDetailInfoVO.getCustomerName(), orderDetailInfoVO.getDeliveryMode(), orderDetailInfoVO.getConsignmentDepartment());
        }
        List<OrderDetailInfoVO> collect = resultSetList.stream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, OrderDetailInfoVO.class);
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void saveOrder(OrderSaveVO orderSaveVO) {
        String[] nullPropertyNames = NullPropertiesUtil.getNullPropertyNames(orderSaveVO);
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderSaveVO, orderInfo, nullPropertyNames);
        List<OrderDetailInfo> orderDetailList = orderSaveVO.getOrderDetailList();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssS");
        String orderNo = LocalDateTime.now().format(dateTimeFormatter);
        String creater = "admin";
        Date now = new Date();

        List<OrderDetailInfo> collect = orderDetailList.stream().map(e -> {
            e.setOrderNo(orderNo);
            e.setCreateUser(creater);
            e.setUpdateUser(creater);
            e.setCreateDate(now);
            e.setUpdateDate(now);
            e.setStockStatus("0");
            return e;
        }).collect(Collectors.toList());
        double sumAmount = orderDetailList.stream().map(e -> e.getAmount()).reduce((a, b) -> a + b).get().doubleValue();
        orderInfo.setOrderNo(orderNo);
        orderInfo.setOrderDate(now);
        orderInfo.setOrderAmount(sumAmount);
        orderInfo.setCreateUser(creater);
        orderInfo.setUpdateUser(creater);
        orderInfo.setCreateDate(now);
        orderInfo.setUpdateDate(now);
        orderInfoService.saveOrderInfo(orderInfo);
        orderDetailInfoService.saveOrderDetailInfo(collect);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void updateOrder(OrderSaveVO orderSaveVO) {
        String[] nullPropertyNames = NullPropertiesUtil.getNullPropertyNames(orderSaveVO);
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderSaveVO, orderInfo, nullPropertyNames);
        List<OrderDetailInfo> orderDetailList = orderSaveVO.getOrderDetailList();
        String orderNo = orderInfo.getOrderNo();
        String creater = "admin";
        Date now = new Date();

        List<OrderDetailInfo> collect = orderDetailList.stream().map(e -> {
            e.setOrderNo(orderNo);
            e.setUpdateUser(creater);
            e.setUpdateDate(now);
            e.setStockStatus("0");
            return e;
        }).collect(Collectors.toList());
        double sumAmount = orderDetailList.stream().map(e -> e.getAmount()).reduce((a, b) -> a + b).get().doubleValue();
        orderInfoService.updateOrderInfo(orderNo, sumAmount, creater);
        orderDetailInfoService.saveOrderDetailInfo(collect);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void saveOrderStockDetail(OrderStockSaveVO orderStockSaveVO) {
        List<OrderStockDetailInfo> orderStockingArrays = orderStockSaveVO.getOrderStockingArrays();
        String updater = "admin";
        Date now = new Date();
        List<OrderStockDetailInfo> collect = orderStockingArrays.stream().map(e -> {
            e.setCreateDate(now);
            e.setCreateUser(updater);
            e.setStatus("0");
            e.setUpdateDate(now);
            e.setUpdateUser(updater);
            return e;
        }).collect(Collectors.toList());
        orderStockDetailInfoService.deleteOrderStockDetail(orderStockSaveVO.getOrderNo(),orderStockSaveVO.getProductType());
        orderStockDetailInfoService.saveOrderStockDetail(collect);
    }

    @Override
    public PageInfo<OrderInfoVO> getWaitPackOrderList(OrderInfo orderInfo) {
        PageHelper.startPage(orderInfo.getPageNum(),orderInfo.getPageSize());
        return new PageInfo<>(orderInfoService.getWaitPackOrderList(orderInfo));
    }
}
