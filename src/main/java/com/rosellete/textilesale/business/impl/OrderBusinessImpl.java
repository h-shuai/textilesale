package com.rosellete.textilesale.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.OrderBusiness;
import com.rosellete.textilesale.model.*;
import com.rosellete.textilesale.service.*;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("orderBusiness")
@Slf4j
public class OrderBusinessImpl implements OrderBusiness {
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderDetailInfoService orderDetailInfoService;
    @Autowired
    private OrderStockDetailInfoService orderStockDetailInfoService;
    @Autowired
    private RejectSuppliesStockDetailService rejectSuppliesStockDetailService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private PackageInventoryInfoService packageInventoryInfoService;


    @Override
    public PageInfo<OrderInfoVO> getOrderList(OrderInfoVO orderInfoVO) {
        Page page = new Page(orderInfoVO.getPageNum(), orderInfoVO.getPageSize());
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderInfoVO);
        OrderInfo orderInfo = new OrderInfo();
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(orderInfoVO, orderInfo, nullPropertyNames);
        BeanUtils.copyProperties(orderInfoVO, customerInfo, nullPropertyNames);
        List<OrderInfo> resultSetList = orderInfoService.findOrderListByCustomerInfo(orderInfo, customerInfo, null, null);
        page.setTotal(orderInfoService.findOrderListSizeByCustomerInfo(orderInfo, customerInfo, null, null));
        List<OrderInfoVO> collect = resultSetList.parallelStream().map(e -> {
            OrderInfoVO temp = new OrderInfoVO();
            BeanUtils.copyProperties(e, temp);
            CustomerInfo info = customerService.findByPrimaryKey(e.getCustomerNo());
            BeanUtils.copyProperties(info, temp);
            List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.findOrderDetailInfoByOrderNo(e.getOrderNo());
            List<OrderDetailInfo> filteredList = orderDetailInfoList.parallelStream().filter(detail -> StringUtils.equals("2", detail.getStockStatus())).collect(Collectors.toList());
            temp.setStockedTypeCount(filteredList.size());
            temp.setStockedSumLength(filteredList.parallelStream().map(detail -> detail.getProductLength()).reduce((a, b) -> a + b).orElse(0.0).doubleValue());
            return temp;
        }).sorted(Comparator.comparing(OrderInfoVO::getOrderDate).reversed()).collect(Collectors.toList());

        page.addAll(collect);
        return new PageInfo<>(page);
    }

    @Override
    public PageInfo<OrderDetailVO> getOrderStockDetailInfo(Integer orderNo) {
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        CustomerInfo customerInfo = customerService.findByPrimaryKey(orderInfo.getCustomerNo());
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.findOrderDetailInfoByOrderNo(orderNo);
        List<OrderDetailVO> parsedList = orderDetailInfoList.parallelStream().map(e -> {
            OrderDetailVO temp = new OrderDetailVO();
            BeanUtils.copyProperties(orderInfo, temp);
            BeanUtils.copyProperties(e, temp);
            BeanUtils.copyProperties(customerInfo, temp);

            String imageUrl;
            if (StringUtils.isBlank(temp.getImageName())) {
                imageUrl = "api/download/notfound.jpg";
            } else {
                imageUrl = new StringBuffer("api/download").append("/").append(temp.getImageName()).toString();
            }
            temp.setUrl(imageUrl);
            List<OrderStockDetail> orderStockDetailInfo = orderStockDetailInfoService.findOrderStockDetailInfo(e.getOrderNo(), e.getProductType());
            temp.setOrderStockingArrays(orderStockDetailInfo);
            temp.setStockedFabricCount(orderStockDetailInfo.size());
            temp.setStockedLength(orderStockDetailInfo.parallelStream().map(stock -> stock.getStockLength()).reduce((a, b) -> a + b).orElse(0.0).doubleValue());
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(parsedList);
    }

    @Override
    public PageInfo<OrderStockDetailVO> getOrderStockDetailInfo(Integer orderNo, String productType) {
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.findOrderDetailInfoByOrderNoAndProductType(orderNo, productType);
        OrderDetailInfo orderDetailInfo = orderDetailInfoList.parallelStream().findFirst().orElse(null);
        List<OrderStockDetail> orderStockDetailInfoList = orderStockDetailInfoService.findOrderStockDetailInfo(orderNo, productType);
        List<OrderStockDetailVO> parsedList = orderStockDetailInfoList.stream().map(e -> {
            OrderStockDetailVO temp = new OrderStockDetailVO();
            BeanUtils.copyProperties(orderInfo, temp);
            BeanUtils.copyProperties(orderDetailInfo, temp);
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(parsedList);
    }

    @Override
    public void confirmOrderStock(Integer orderNo) {
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        SysConfig orderConfirmThreshold = sysConfigService.findByCodeName("OrderConfirmThreshold");
        if (null != orderConfirmThreshold) {
            double threshold = Double.parseDouble(orderConfirmThreshold.getCodeValue());
            List<OrderStockDetail> list = orderStockDetailInfoService.findAllByOrderNo(orderNo);
            double stocked = list.parallelStream().map(e -> e.getStockLength()).reduce((a, b) -> a + b).orElse(0.0).doubleValue();
            double reserve = orderInfo.getReserveSumLength().doubleValue();
            if (Math.abs(100.0 * (stocked - reserve) / reserve) > threshold) {
                throw new IllegalStateException("配货误差不能大于" + threshold);
            }
        }
        orderInfo.setOrderStatus("3");
        orderInfo.setUpdateDate(new Date());
        orderInfoService.saveOrderInfo(orderInfo);
        this.saveAccountDetail(orderInfo);
    }

    private void saveAccountDetail(OrderInfo orderInfo) {
        CustomerInfo customerInfo = customerService.findByPrimaryKey(orderInfo.getCustomerNo());
        AccountVO accountVO = new AccountVO();
        accountVO.setCustomerNo(String.valueOf(orderInfo.getCustomerNo()));
        accountVO.setCustomerName(customerInfo.getCustomerName());
        accountVO.setPayFee(-orderInfo.getOrderAmount());
        accountVO.setPayMethod(4l);
        accountVO.setRemark("订单号：" + orderInfo.getOrderNo());
        accountService.saveAccountInfo(accountVO);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void orderRestock(Integer orderNo) {
        orderInfoService.updateOrderStatus(orderNo, "1");
        orderStockDetailInfoService.deleteOrderStockDetailByOrderNo(orderNo);
    }

    @Override
    public PageInfo<OrderDetailVO> getOrderDetailList(OrderDetailVO orderDetailInfoVO) {
        Page page = new Page(orderDetailInfoVO.getPageNum(), orderDetailInfoVO.getPageSize());
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
            resultSetList = orderDetailInfoService.findOrderDetailInfo(orderDetailInfo,
                    orderDetailInfoVO.getCustomerName(), orderDetailInfoVO.getDeliveryMode(),
                    orderDetailInfoVO.getConsignmentDepartment(), startDate, endDate);
            page.setTotal(orderDetailInfoService.findOrderDetailInfoListSize(orderDetailInfo,
                    orderDetailInfoVO.getCustomerName(), orderDetailInfoVO.getDeliveryMode(),
                    orderDetailInfoVO.getConsignmentDepartment(), startDate, endDate));
        } else {
            resultSetList = orderDetailInfoService.findOrderDetailInfo(orderDetailInfo,
                    orderDetailInfoVO.getCustomerName(), orderDetailInfoVO.getDeliveryMode(),
                    orderDetailInfoVO.getConsignmentDepartment(), null, null);
            page.setTotal(orderDetailInfoService.findOrderDetailInfoListSize(orderDetailInfo,
                    orderDetailInfoVO.getCustomerName(), orderDetailInfoVO.getDeliveryMode(),
                    orderDetailInfoVO.getConsignmentDepartment(), null, null));
        }
        List<OrderDetailVO> collect = resultSetList.parallelStream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, OrderDetailVO.class);
        }).collect(Collectors.toList());
        page.addAll(collect);
        return new PageInfo<>(page);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void saveOrder(OrderSaveVO orderSaveVO) {
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderSaveVO);
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderSaveVO, orderInfo, nullPropertyNames);
        List<OrderDetailInfo> orderDetailList = orderSaveVO.getOrderDetailList().parallelStream().filter(e -> StringUtils.isNotBlank(e.getProductType())).collect(Collectors.toList());
        Integer customerNo = orderSaveVO.getCustomerNo();
        if (null == customerNo) {
            customerNo = customerService.saveCustomer(orderSaveVO.getCustomerName(), orderSaveVO.getCustomerPhoneNo());
            orderInfo.setCustomerNo(customerNo);
        }
        Integer orderNo = this.getSequenceNo();
        String creator = "admin";
        Date now = new Date();
        OrderDetailInfo tmp;
        double sumAmount = 0.0;
        double sumLength = 0.0;
        long typeCount = orderDetailList.parallelStream().map(e -> e.getProductType()).distinct().count();
        for (int i = 0; i < orderDetailList.size(); i++) {
            tmp = orderDetailList.get(i);
            tmp.setOrderNo(orderNo);
            tmp.setImageName(packageInventoryInfoService.findLatestImageNameByProductType(tmp.getProductType()));
            tmp.setCreateUser(creator);
            tmp.setUpdateUser(creator);
            tmp.setCreateDate(now);
            tmp.setUpdateDate(now);
            tmp.setStockStatus("0");
            sumAmount += tmp.getAmount();
            sumLength += tmp.getProductLength();
        }
        orderInfo.setOrderNo(orderNo);
        orderInfo.setOrderDate(now);
        orderInfo.setOrderAmount(sumAmount);
        orderInfo.setReserveSumLength(sumLength);
        orderInfo.setReserveTypeCount((int) typeCount);
        orderInfo.setClerkName(creator);
        orderInfo.setCreateUser(creator);
        orderInfo.setUpdateUser(creator);
        orderInfo.setCreateDate(now);
        orderInfo.setUpdateDate(now);
        orderInfoService.saveOrderInfo(orderInfo);
        orderDetailInfoService.saveOrderDetailInfo(orderDetailList);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void updateOrder(OrderSaveVO orderSaveVO) {
        Integer orderNo = orderSaveVO.getOrderNo();
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderSaveVO);
        BeanUtils.copyProperties(orderSaveVO, orderInfo, nullPropertyNames);
        List<OrderDetailInfo> orderDetailList = orderSaveVO.getOrderDetailList().parallelStream().filter(e -> StringUtils.isNotBlank(e.getProductType())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(orderDetailList)){
            String creator = "admin";
            Date now = new Date();
            List<OrderDetailInfo> orderDetailInfos = new ArrayList<>(orderDetailList.size());
            orderDetailList.parallelStream().forEach(e -> {
                OrderDetailInfo orderDetailInfo = orderDetailInfoService.findOrderDetailInfoByOrderNoAndProductType(e.getOrderNo(), e.getProductType()).
                        parallelStream().findFirst().orElse(null);
                String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(e);
                if (null!=orderDetailInfo){
                    BeanUtils.copyProperties(e, orderDetailInfo, nullOrBlankPropertyNames);
                }else {
                    orderDetailInfo.setOrderNo(orderNo);
                    orderDetailInfo.setCreateDate(now);
                    orderDetailInfo.setStockStatus("0");
                    orderInfo.setOrderStatus("1");
                }
                orderDetailInfo.setUpdateUser(creator);
                orderDetailInfo.setUpdateDate(now);
                orderDetailInfos.add(orderDetailInfo);
            });
            double sumAmount = orderDetailList.parallelStream().map(e -> e.getAmount()).reduce((a, b) -> a + b).get().doubleValue();
            orderInfo.setOrderAmount(sumAmount);
            orderInfo.setUpdateUser(creator);
            orderInfo.setUpdateDate(now);
            orderInfoService.saveOrderInfo(orderInfo);
            orderDetailInfoService.saveOrderDetailInfo(orderDetailInfos);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void saveOrderStockDetail(OrderStockSaveVO orderStockSaveVO) {
        Integer orderNo = orderStockSaveVO.getOrderNo();
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        String[] nullPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(orderStockSaveVO);
        BeanUtils.copyProperties(orderStockSaveVO, orderInfo, nullPropertyNames);
        List<OrderDetailVO> orderDetailList = orderStockSaveVO.getOrderDetailList().parallelStream().
                filter(e -> StringUtils.isNotBlank(e.getProductType()) && !CollectionUtils.isEmpty(e.getOrderStockingArrays())).
                collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(orderDetailList)){
            List<OrderDetailInfo> orderDetailInfos = new ArrayList<>(orderDetailList.size());
            List<OrderStockDetail> existedOrderStockList = new ArrayList<>(10);
            List<OrderStockDetail> toBeInsertOrderStockList = new ArrayList<>(10);
            String updater = "admin";
            Date now = new Date();
            orderDetailList.parallelStream().forEach(e -> {
                        String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(e);
                        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.findOrderDetailInfoByOrderNoAndProductType(e.getOrderNo(), e.getProductType());
                        OrderDetailInfo orderDetailInfo = orderDetailInfoList.parallelStream().findFirst().orElse(null);
                        BeanUtils.copyProperties(e, orderDetailInfo, nullOrBlankPropertyNames);
                        List<OrderStockDetail> orderStockingArrays = e.getOrderStockingArrays().parallelStream().
                                filter(element -> null != element.getStockLength() && 0.0D != element.getStockLength()).
                                collect(Collectors.toList());
                        Double stockedLength = 0.0D;
                        for (OrderStockDetail stock : orderStockingArrays) {
                            List<OrderStockDetail> orderStockDetailInfo = orderStockDetailInfoService.findOrderStockDetailInfo(orderNo, e.getProductType());
                            stock.setOrderNo(e.getOrderNo());
                            stock.setCreateDate(now);
                            stock.setCreateUser(updater);
                            stock.setStatus("0");
                            stock.setUpdateDate(now);
                            stock.setUpdateUser(updater);
                            stockedLength += stock.getStockLength();
                            toBeInsertOrderStockList.add(stock);
                            if (!CollectionUtils.isEmpty(orderStockDetailInfo)) {
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
            if (orderDetailInfos.parallelStream().filter(e -> StringUtils.equals("2", e.getStockStatus())).count() >= orderDetailInfos.size()) {
                orderInfo.setOrderStatus("2");
                orderInfo.setUpdateDate(now);
                orderInfo.setStockDate(now);
                orderInfo.setStocker(updater);
            }
            orderInfoService.saveOrderInfo(orderInfo);
        }
    }

    @Override
    public PagedListHolder<OrderInfoVO> getWaitPackCustomerList(OrderInfoVO orderInfoVO) {
        PagedListHolder<OrderInfoVO> pagedListHolder = new PagedListHolder<>(orderInfoService.getWaitPackCustomerList(orderInfoVO));
        pagedListHolder.setPage(orderInfoVO.getPageNum() - 1);
        pagedListHolder.setPageSize(orderInfoVO.getPageSize());
        return pagedListHolder;
    }

    @Override
    public PackInfoVO getTotalCount(Integer customer, String businessType) {
        return orderInfoService.getTotalCount(customer, businessType);
    }

    @Override
    public List<PackInfoVO> getPieceList(Integer customer, String businessType,String address) {
        List<PackInfoVO> returnList = orderInfoService.getWaitPieceList(customer, businessType,address);
        for (PackInfoVO packInfoVO : returnList) {
            for (PackSubInfoVO packSubInfoVO : packInfoVO.getPackSubInfoVOS()) {
                if (businessType.equals("0")) {
                    packSubInfoVO.setPieceOptions(orderStockDetailInfoService.getPieceList(packInfoVO.getOrderNo(), packSubInfoVO.getColthModel()));
                } else {
                    packSubInfoVO.setPieceOptions(rejectSuppliesStockDetailService.getPieceList(packInfoVO.getOrderNo(), packSubInfoVO.getColthModel()));
                }
            }
        }
        return returnList;
    }

    @Override
    public RestResponse getWaitSettleList(OrderInfoVO orderInfoVO) {
        return new RestResponse(orderInfoService.getWaitSettleList(orderInfoVO));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Integer copyAndCreateOrder(Integer orderNo) {
        OrderInfo orderInfo = orderInfoService.findByPrimaryKey(orderNo);
        OrderInfo newOrder = new OrderInfo();
        BeanUtils.copyProperties(orderInfo, newOrder);
        Date now = new Date();
        Integer newOrderNo = this.getSequenceNo();
        String operator = "admin";
        newOrder.setUpdateDate(now);
        newOrder.setCreateDate(now);
        newOrder.setOrderDate(now);
        newOrder.setReserveSumLength(0.0);
        newOrder.setOrderStatus("0");
        newOrder.setCreateUser(operator);
        newOrder.setUpdateUser(operator);
        newOrder.setOrderAmount(0.0);
        newOrder.setOrderNo(newOrderNo);
        List<OrderDetailInfo> orderDetailList = orderDetailInfoService.findOrderDetailInfoByOrderNo(orderNo);
        List<OrderDetailInfo> toBeInsertList = new ArrayList<>(orderDetailList.size());
        OrderDetailInfo detailInfo;
        for (int i = 0; i < orderDetailList.size(); i++) {
            detailInfo = new OrderDetailInfo();
            BeanUtils.copyProperties(orderDetailList.get(i), detailInfo);
            detailInfo.setOrderNo(newOrderNo);
            detailInfo.setId(null);
            detailInfo.setProductLength(0.0);
            detailInfo.setStockStatus("0");
            detailInfo.setAmount(0.0);
            detailInfo.setUpdateDate(now);
            detailInfo.setCreateDate(now);
            detailInfo.setCreateUser(operator);
            detailInfo.setUpdateUser(operator);
            toBeInsertList.add(detailInfo);
        }
        orderDetailInfoService.saveOrderDetailInfo(toBeInsertList);
        orderInfoService.saveOrderInfo(newOrder);
        return newOrderNo;
    }

    @Override
    public Integer getSequenceNo() {
        return orderInfoService.getMaxOrderNo() + 1;
    }
}
