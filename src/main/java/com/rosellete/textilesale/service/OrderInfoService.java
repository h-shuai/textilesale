package com.rosellete.textilesale.service;

import com.alibaba.fastjson.JSONObject;
import com.rosellete.textilesale.dao.OrderInfoDao;
import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.model.OrderInfo;
import com.rosellete.textilesale.vo.OrderInfoVO;
import com.rosellete.textilesale.vo.PackInfoVO;
import com.rosellete.textilesale.vo.PackSubInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoDao orderInfoDao;


    public OrderInfo findByPrimaryKey(String orderNo) {
        Optional<OrderInfo> orderInfo = orderInfoDao.findById(orderNo);
        return orderInfo.orElse(null);
    }

    public List<OrderInfo> findOrderListByCustomerInfo(OrderInfo orderInfo, CustomerInfo customerInfo, Date startDate, Date endDate) {
        return orderInfoDao.findPagedOrderList(orderInfo.getOrderNo(),customerInfo.getCustomerNo(), customerInfo.getName(),
                customerInfo.getPhone(), orderInfo.getOrderStatus(), startDate, endDate,
                (orderInfo.getPageNum()-1)*orderInfo.getPageSize(),orderInfo.getPageSize());
    }

    public long findOrderListSizeByCustomerInfo(OrderInfo orderInfo,CustomerInfo customerInfo, Date startDate, Date endDate) {
        int size = orderInfoDao.findOrderList(orderInfo.getOrderNo(),customerInfo.getCustomerNo(), customerInfo.getName(),
                customerInfo.getPhone(), orderInfo.getOrderStatus(), startDate, endDate).size();
        return size;

    }

    public void updateOrderStatus(String orderNo, String orderStatus) {
        orderInfoDao.updateOrderStatus(orderNo, orderStatus, "admin");
    }

    public void saveOrderInfo(OrderInfo orderInfo) {
        orderInfoDao.save(orderInfo);
    }

    public void updateOrderInfo(String orderNo, String orderStatus, Double sumAmount, String updater) {
        orderInfoDao.updateStatusAndAmount(orderNo, orderStatus, sumAmount, updater);
    }

    public List<OrderInfoVO> getWaitPackCustomerList(OrderInfoVO orderInfoVO) {
        List<Map<String, Object>> maps = orderInfoDao.getWaitPackCustomer(orderInfoVO.getCustomerName()) ;
        List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            OrderInfoVO newInfoVO = JSONObject.parseObject(JSONObject.toJSONString(map), OrderInfoVO.class);
            orderInfoVOList.add(newInfoVO);
        }
        return orderInfoVOList;
    }

    private List<OrderInfoVO> getWaitPackOrderList(OrderInfo orderInfo) {
        List<Map<String, Object>> maps = orderInfoDao.getWaitPackOrderList(orderInfo.getOrderNo(),orderInfo.getCustomerNo());
        List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            OrderInfoVO orderInfoVO = JSONObject.parseObject(JSONObject.toJSONString(map), OrderInfoVO.class);
            orderInfoVOList.add(orderInfoVO);
        }
        return orderInfoVOList;
    }

    public List<String> getTotalCount(Integer customer){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCustomerNo(customer);
        orderInfo.setOrderNo(null);
        List<OrderInfoVO> orderInfoVOList = this.getWaitPackOrderList(orderInfo);
        List<String> orderNos = new ArrayList<>();
        for (OrderInfoVO orderInfoVO : orderInfoVOList){
            orderNos.add(orderInfoVO.getOrderNo());
        }
        return orderInfoDao.getTotalCount(orderNos,String.valueOf(customer));
    }

    public List<PackInfoVO> getWaitPieceList(Integer customer) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCustomerNo(customer);
        orderInfo.setOrderNo(null);
        List<OrderInfoVO> orderInfoVOList = this.getWaitPackOrderList(orderInfo);
        List<String> orderNos = new ArrayList<>();
        for (OrderInfoVO orderInfoVO : orderInfoVOList){
            orderNos.add(orderInfoVO.getOrderNo());
        }
        List<PackInfoVO> returnList = new ArrayList<>();
        List<Map<String, Object>> pieceList = orderInfoDao.getWaitPieceList(orderNos);
        for (String orderNo : orderNos){
            List<PackSubInfoVO> packSubInfoVOS = new ArrayList<>();
            PackInfoVO packInfoVO = new PackInfoVO();
            packInfoVO.setOrderNo(orderNo);
            for (Map<String, Object> map : pieceList){
                if (orderNo.equals(map.get("orderNo"))){
                    PackSubInfoVO packSubInfoVO = new PackSubInfoVO();
                    packSubInfoVO.setColthModel((String)map.get("colthModel"));
                    packSubInfoVO.setPicurl((String)map.get("picurl"));
                    packSubInfoVOS.add(packSubInfoVO);
                }
            }
            packInfoVO.setPackSubInfoVOS(packSubInfoVOS);
            returnList.add(packInfoVO);
        }
        return returnList;
    }

    public List<OrderInfoVO> getWaitSettleList(OrderInfoVO orderInfoVO){
        return orderInfoDao.getWaitSettleList(orderInfoVO.getOrderNo(),orderInfoVO.getCustomerName());
    }
}
