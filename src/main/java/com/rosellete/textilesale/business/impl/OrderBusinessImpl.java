package com.rosellete.textilesale.business.impl;

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
import com.rosellete.textilesale.vo.OrderDetailInfoVO;
import com.rosellete.textilesale.vo.OrderInfoVO;
import com.rosellete.textilesale.vo.OrderStockDetailInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        Date startDate=orderInfoVO.getEndDate(),endDate=orderInfoVO.getStartDate();
        if (!(null == startDate && null == endDate)){
            if (null!=endDate){
                Calendar calendarInstance = Calendar.getInstance();
                calendarInstance.setTime(endDate);
                calendarInstance.add(Calendar.DATE,1);
                endDate= calendarInstance.getTime();
            }
            orderList = orderInfoService.getOrderListByCustomerInfoAndDate(orderInfo, startDate, endDate);
        }else {
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
    public OrderInfoVO getOrderDetailInfo(String orderNo) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderNo);
        List<OrderDetailInfo> orderDetailInfo = orderDetailInfoService.getOrderDetailInfoByOrderNo(orderNo);
        List<OrderDetailInfoVO> collectOrderDetailInfo = convertOrderDetail2VO(orderDetailInfo);
        OrderInfoVO result = new OrderInfoVO();
        BeanUtils.copyProperties(orderInfo, result);
        result.setOrderDetailInfoVOList(collectOrderDetailInfo);
        return result;
    }

    @Override
    public OrderInfoVO getOrderDetailInfo(String orderNo, String productType) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderNo);
        List<OrderDetailInfo> orderDetailInfo = orderDetailInfoService.getOrderDetailInfoByOrderNoAndProductType(orderNo,productType);
        List<OrderDetailInfoVO> collectOrderDetailInfo = convertOrderDetail2VO(orderDetailInfo);
        OrderInfoVO result = new OrderInfoVO();
        BeanUtils.copyProperties(orderInfo, result);
        result.setOrderDetailInfoVOList(collectOrderDetailInfo);
        return result;
    }

    private static List<OrderDetailInfoVO> convertOrderDetail2VO(List<OrderDetailInfo> orderDetailInfo) {
        return orderDetailInfo.stream().map(e -> {
            OrderDetailInfoVO temp = new OrderDetailInfoVO();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
    }

    @Override
    public OrderInfoVO getOrderStockDetailInfo(String orderNo) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderNo);
        List<OrderDetailInfo> orderDetailInfo = orderDetailInfoService.getOrderDetailInfoByOrderNo(orderNo);
        List<OrderDetailInfoVO> parsedOrderDetailInfo=new ArrayList<>(10);
        orderDetailInfo.stream().forEach(e->{
            List<OrderStockDetailInfo> orderStockDetailInfo=orderStockDetailInfoService.getOrderStockDetailInfo(orderNo,e.getProductType());
            List<OrderStockDetailInfoVO> collectOrderStockDetailInfo = orderStockDetailInfo.stream().map(stock -> {
                OrderStockDetailInfoVO stockDetail = new OrderStockDetailInfoVO();
                BeanUtils.copyProperties(stock, stockDetail);
                return stockDetail;
            }).collect(Collectors.toList());
            OrderDetailInfoVO temp = new OrderDetailInfoVO();
            BeanUtils.copyProperties(e, temp);
            temp.setOrderStockDetailInfoVOList(collectOrderStockDetailInfo);
            parsedOrderDetailInfo.add(temp);
        });
        OrderInfoVO result = new OrderInfoVO();
        BeanUtils.copyProperties(orderInfo, result);
        result.setOrderDetailInfoVOList(parsedOrderDetailInfo);
        return result;
    }

    @Override
    public OrderInfoVO getOrderStockDetailInfo(String orderNo,String productType) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderNo);
        List<OrderDetailInfo> orderDetailInfo = orderDetailInfoService.getOrderDetailInfoByOrderNoAndProductType(orderNo,productType);
        List<OrderStockDetailInfo> orderStockDetailInfo=orderStockDetailInfoService.getOrderStockDetailInfo(orderNo,productType);

        List<OrderStockDetailInfoVO> collectOrderStockDetailInfo = orderStockDetailInfo.stream().map(e -> {
            OrderStockDetailInfoVO temp = new OrderStockDetailInfoVO();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());

        List<OrderDetailInfoVO> collectOrderDetailInfo = orderDetailInfo.stream().map(e -> {
            OrderDetailInfoVO temp = new OrderDetailInfoVO();
            BeanUtils.copyProperties(e, temp);
            temp.setOrderStockDetailInfoVOList(collectOrderStockDetailInfo);
            return temp;
        }).collect(Collectors.toList());

        OrderInfoVO result = new OrderInfoVO();
        BeanUtils.copyProperties(orderInfo, result);
        result.setOrderDetailInfoVOList(collectOrderDetailInfo);
        return result;
    }
}
