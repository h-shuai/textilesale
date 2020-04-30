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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderInfoVO);
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderInfoVO, orderInfo, nullPropertyNames);
        List<OrderInfo> orderList;
        Date startDate = orderInfoVO.getStartDate(), endDate = orderInfoVO.getEndDate();
        if (!(null == startDate && null == endDate)) {
            if (null != endDate) {
                Calendar calendarInstance = Calendar.getInstance();
                calendarInstance.setTime(endDate);
                calendarInstance.add(Calendar.DATE, 1);
                endDate = calendarInstance.getTime();
            }
            orderList = orderInfoService.findOrderListByCustomerInfo(orderInfo, startDate, endDate);
        } else {
            orderList = orderInfoService.findOrderListByCustomerInfo(orderInfo, null, null);
        }
        List<OrderInfoVO> collect = orderList.stream().map(e -> {
            OrderInfoVO temp = new OrderInfoVO();
            BeanUtils.copyProperties(e, temp);
            List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.findOrderDetailInfoByOrderNo(e.getOrderNo());
            temp.setReserveTypeCount(orderDetailInfoList.size());
            temp.setReserveSumLength(orderDetailInfoList.stream().map(detail -> detail.getProductLength()).reduce((a, b) -> a + b).orElse(0.0).doubleValue());
            List<OrderDetailInfo> filteredList = orderDetailInfoList.stream().filter(detail -> StringUtils.equals("2", detail.getStockStatus())).collect(Collectors.toList());
            temp.setStockedTypeCount(filteredList.size());
            temp.setStockedSumLength(filteredList.stream().map(detail -> detail.getProductLength()).reduce((a, b) -> a + b).orElse(0.0).doubleValue());
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public PageInfo<OrderDetailInfoVO> getOrderStockDetailInfo(String orderNo) {
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.findOrderDetailInfoByOrderNo(orderNo);
        List<OrderDetailInfoVO> parsedList = orderDetailInfoList.stream().map(e -> {
            OrderDetailInfoVO temp = new OrderDetailInfoVO();
            BeanUtils.copyProperties(orderInfo, temp);
            BeanUtils.copyProperties(e, temp);
            List<OrderStockDetailInfo> orderStockDetailInfo = orderStockDetailInfoService.findOrderStockDetailInfo(e.getOrderNo(), e.getProductType());
            temp.setOrderStockingArrays(orderStockDetailInfo);
            temp.setStockedFabricCount(orderStockDetailInfo.size());
            temp.setStockedLength(orderStockDetailInfo.stream().map(stock -> stock.getStockLength()).reduce((a, b) -> a + b).orElse(0.0).doubleValue());
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(parsedList);
    }

    @Override
    public PageInfo<OrderStockDetailInfoVO> getOrderStockDetailInfo(String orderNo, String productType) {
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.findOrderDetailInfoByOrderNoAndProductType(orderNo, productType);
        OrderDetailInfo orderDetailInfo = orderDetailInfoList.stream().findFirst().orElse(null);
        List<OrderStockDetailInfo> orderStockDetailInfoList = orderStockDetailInfoService.findOrderStockDetailInfo(orderNo, productType);
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
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderDetailInfoVO);
        OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
        BeanUtils.copyProperties(orderDetailInfoVO, orderDetailInfo, nullPropertyNames);
        List<Map<String, Object>> resultSetList;
        Date startDate = orderDetailInfoVO.getStartDate(), endDate = orderDetailInfoVO.getEndDate();
        if (!(null == startDate && null == endDate)) {
            if (null != endDate) {
                Calendar calendarInstance = Calendar.getInstance();
                calendarInstance.setTime(endDate);
                calendarInstance.add(Calendar.DATE, 1);
                endDate = calendarInstance.getTime();
            }
            resultSetList = orderDetailInfoService.findOrderDetailInfo(orderDetailInfo.getOrderNo(), orderDetailInfo.getProductType(),
                    orderDetailInfoVO.getCustomerName(), orderDetailInfoVO.getDeliveryMode(),
                    orderDetailInfoVO.getConsignmentDepartment(), startDate, endDate);
        } else {
            resultSetList = orderDetailInfoService.findOrderDetailInfo(orderDetailInfo.getOrderNo(), orderDetailInfo.getProductType(),
                    orderDetailInfoVO.getCustomerName(), orderDetailInfoVO.getDeliveryMode(),
                    orderDetailInfoVO.getConsignmentDepartment(), null, null);
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
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderSaveVO);
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
        orderInfo.setClerkName(creater);
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
        String orderNo = orderSaveVO.getOrderNo();
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderSaveVO);
        BeanUtils.copyProperties(orderSaveVO, orderInfo, nullPropertyNames);
        List<OrderDetailInfo> orderDetailList = orderSaveVO.getOrderDetailList();
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
        orderInfo.setOrderAmount(sumAmount);
        orderInfoService.saveOrderInfo(orderInfo);
        orderDetailInfoService.saveOrderDetailInfo(collect);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void saveOrderStockDetail(OrderStockSaveVO orderStockSaveVO) {
        String orderNo = orderStockSaveVO.getOrderNo();
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderStockSaveVO);
        BeanUtils.copyProperties(orderStockSaveVO, orderInfo, nullPropertyNames);
        List<OrderDetailInfoVO> orderDetailList = orderStockSaveVO.getOrderDetailList();
        List<OrderDetailInfo> orderDetailInfos = new ArrayList<>(orderDetailList.size());
        List<OrderStockDetailInfo> existedOrderStockList = new ArrayList<>(10);
        List<OrderStockDetailInfo> toBeInsertOrderStockList = new ArrayList<>(10);
        String updater = "admin";
        Date now = new Date();
        orderDetailList.stream().forEach(e -> {
                    String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(e);
                    List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.findOrderDetailInfoByOrderNoAndProductType(e.getOrderNo(), e.getProductType());
                    OrderDetailInfo orderDetailInfo = orderDetailInfoList.stream().findFirst().orElse(null);
                    BeanUtils.copyProperties(e, orderDetailInfo, nullOrBlankPropertyNames);
                    List<OrderStockDetailInfo> orderStockingArrays = e.getOrderStockingArrays();
                    Double stockedLength = 0.0D;
                    for (OrderStockDetailInfo stock : orderStockingArrays) {
                        List<OrderStockDetailInfo> orderStockDetailInfo = orderStockDetailInfoService.findOrderStockDetailInfo(orderNo, e.getProductType());
                        if (CollectionUtils.isEmpty(orderStockDetailInfo)) {
                            stock.setCreateDate(now);
                            stock.setCreateUser(updater);
                            stock.setStatus("0");
                            stock.setUpdateDate(now);
                            stock.setUpdateUser(updater);
                            stockedLength += stock.getStockLength();
                            toBeInsertOrderStockList.add(stock);
                        } else {
                            existedOrderStockList.addAll(orderStockDetailInfo);
                        }
                    }
                    if (stockedLength.compareTo(0.0D) > 0 && stockedLength.compareTo(orderDetailInfo.getProductLength()) >= 0D) {
                        orderDetailInfo.setStockStatus("2");
                    } else {
                        orderDetailInfo.setStockStatus("1");
                    }
                    orderDetailInfo.setUpdateUser(updater);
                    orderDetailInfo.setUpdateDate(now);
                    orderDetailInfos.add(orderDetailInfo);
                }
        );
        orderStockDetailInfoService.deleteOrderStockDetail(existedOrderStockList);
        orderStockDetailInfoService.saveOrderStockDetail(toBeInsertOrderStockList);
        orderDetailInfoService.saveOrderDetailInfo(orderDetailInfos);
        orderInfoService.saveOrderInfo(orderInfo);

    }

    @Override
    public PageInfo<OrderInfoVO> getWaitPackOrderList(OrderInfo orderInfo) {
        PageHelper.startPage(orderInfo.getPageNum(), orderInfo.getPageSize());
        return new PageInfo<>(orderInfoService.getWaitPackOrderList(orderInfo));
    }

    @Override
    public List<String> getTotalCount(String orderNo) {
        return orderInfoService.getTotalCount(orderNo);
    }

    @Override
    public List<PackInfoVO> getPieceList(String orderNo) {
        List<PackInfoVO> returnList = orderInfoService.getWaitPieceList(orderNo);
        for (PackInfoVO packInfoVO : returnList){
            packInfoVO.setPieceOptions(orderStockDetailInfoService.getPieceList(orderNo,packInfoVO.getColthModel()));
        }
        return returnList;
    }
}
