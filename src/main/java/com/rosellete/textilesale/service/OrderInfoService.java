package com.rosellete.textilesale.service;

import com.alibaba.fastjson.JSONObject;
import com.rosellete.textilesale.dao.OrderInfoDao;
import com.rosellete.textilesale.dao.RejectRecordDao;
import com.rosellete.textilesale.dao.SupplierDao;
import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.model.OrderInfo;
import com.rosellete.textilesale.vo.OrderInfoVO;
import com.rosellete.textilesale.vo.PackInfoVO;
import com.rosellete.textilesale.vo.PackSubInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private RejectRecordDao rejectRecordDao;
    @Autowired
    private SupplierDao supplierDao;


    public OrderInfo findByPrimaryKey(Integer orderNo) {
        Optional<OrderInfo> orderInfo = orderInfoDao.findById(orderNo);
        return orderInfo.orElse(null);
    }

    public List<OrderInfo> findOrderListByCustomerInfo(OrderInfo orderInfo, CustomerInfo customerInfo, Date startDate, Date endDate) {
        return orderInfoDao.findPagedOrderList(orderInfo.getOrderNo(),customerInfo.getCustomerNo(), customerInfo.getCustomerName(),
                customerInfo.getCustomerPhoneNo(), orderInfo.getOrderStatus(), startDate, endDate,
                (orderInfo.getPageNum()-1)*orderInfo.getPageSize(),orderInfo.getPageSize());
    }

    public long findOrderListSizeByCustomerInfo(OrderInfo orderInfo,CustomerInfo customerInfo, Date startDate, Date endDate) {
        int size = orderInfoDao.findOrderList(orderInfo.getOrderNo(),customerInfo.getCustomerNo(), customerInfo.getCustomerName(),
                customerInfo.getCustomerPhoneNo(), orderInfo.getOrderStatus(), startDate, endDate).size();
        return size;

    }

    public void updateOrderStatus(Integer orderNo, String orderStatus) {
        orderInfoDao.updateOrderStatus(orderNo, orderStatus, "admin");
    }

    public void saveOrderInfo(OrderInfo orderInfo) {
        orderInfoDao.save(orderInfo);
    }

    public void updateOrderInfo(Integer orderNo, String orderStatus, Double sumAmount, String updater) {
        orderInfoDao.updateStatusAndAmount(orderNo, orderStatus, sumAmount, updater);
    }

    public List<OrderInfoVO> getWaitPackCustomerList(OrderInfoVO orderInfoVO) {
        List<Map<String, Object>> maps = orderInfoDao.getWaitPackCustomer(orderInfoVO.getCustomerName()) ;
        List<Map<String, Object>> supplierMaps = rejectRecordDao.getWaitPackSupplier(orderInfoVO.getCustomerName());
        List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            OrderInfoVO newInfoVO = JSONObject.parseObject(JSONObject.toJSONString(map), OrderInfoVO.class);
            newInfoVO.setBusinessType("0");
            orderInfoVOList.add(newInfoVO);
        }
        for (Map<String, Object> map : supplierMaps) {
            OrderInfoVO newInfoVO = JSONObject.parseObject(JSONObject.toJSONString(map), OrderInfoVO.class);
            newInfoVO.setBusinessType("1");
            newInfoVO.setOrderAmount(null);
            newInfoVO.setConsignmentDepartment(null);
            orderInfoVOList.add(newInfoVO);
        }
        return orderInfoVOList;
    }

    private List<OrderInfoVO> getWaitPackOrderList(OrderInfoVO orderInfo) {
        List<Map<String, Object>> maps;
        if (orderInfo.getBusinessType().equals("0")) {
            maps = orderInfoDao.getWaitPackOrderList(orderInfo.getOrderNo(),orderInfo.getCustomerNo(),orderInfo.getDeliveryAddress());
        } else {
            maps = rejectRecordDao.getWaitPackRejectList(orderInfo.getOrderNo(),orderInfo.getCustomerNo(),orderInfo.getDeliveryAddress());
        }
        List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            OrderInfoVO orderInfoVO = JSONObject.parseObject(JSONObject.toJSONString(map), OrderInfoVO.class);
            orderInfoVOList.add(orderInfoVO);
        }
        return orderInfoVOList;
    }

    public PackInfoVO getTotalCount(Integer customer,String businessType){
        PackInfoVO packInfoVO = new PackInfoVO();
        OrderInfoVO orderInfo = new OrderInfoVO();
        orderInfo.setCustomerNo(customer);
        orderInfo.setOrderNo(null);
        orderInfo.setBusinessType(businessType);
        orderInfo.setDeliveryAddress(null);
        List<OrderInfoVO> orderInfoVOList = this.getWaitPackOrderList(orderInfo);
        List<Integer> orderNos = new ArrayList<>();
        for (OrderInfoVO orderInfoVO : orderInfoVOList){
            orderNos.add(orderInfoVO.getOrderNo());
        }
        List<String> strs;
        List<String> addressOptions = new ArrayList<>();
        if (businessType.equals("0")) {
            strs = orderInfoDao.getTotalCount(orderNos,String.valueOf(customer));
            addressOptions = orderInfoDao.getAddressByOrderNos(orderNos);
        } else {
            strs = rejectRecordDao.getRejectTotalCount(orderNos,String.valueOf(customer));
            String address = supplierDao.findBySupplierNo(String.valueOf(customer)).getSupplierAddress();
            addressOptions.add(address);
        }
        packInfoVO.setModelCount(strs.get(0));
        packInfoVO.setPieceCountVue(strs.get(1));
        packInfoVO.setPackageCount(strs.get(2));
        packInfoVO.setRiceNum(strs.get(3));
        packInfoVO.setPieceNum(strs.get(4));
        packInfoVO.setAddressOptions(addressOptions);
        return packInfoVO;
    }

    public List<PackInfoVO> getWaitPieceList(Integer customer,String businessType,String address) {
        OrderInfoVO orderInfo = new OrderInfoVO();
        orderInfo.setCustomerNo(customer);
        orderInfo.setOrderNo(null);
        orderInfo.setDeliveryAddress(address);
        orderInfo.setBusinessType(businessType);
        List<OrderInfoVO> orderInfoVOList = this.getWaitPackOrderList(orderInfo);
        List<Integer> orderNos = new ArrayList<>();
        for (OrderInfoVO orderInfoVO : orderInfoVOList){
            orderNos.add(orderInfoVO.getOrderNo());
        }
        List<PackInfoVO> returnList = new ArrayList<>();
        List<Map<String, String>> pieceList;
        if (businessType.equals("0")) {
            pieceList = orderInfoDao.getWaitPieceList(orderNos);
        } else {
            pieceList = rejectRecordDao.getRejectWaitPieceList(orderNos);
        }
        for (Integer orderNo : orderNos){
            List<PackSubInfoVO> packSubInfoVOS = new ArrayList<>();
            PackInfoVO packInfoVO = new PackInfoVO();
            packInfoVO.setOrderNo(orderNo);
            for (Map<String, String> map : pieceList){
                if (String.valueOf(orderNo).equals(String.valueOf(map.get("orderNo")))){
                    PackSubInfoVO packSubInfoVO = new PackSubInfoVO();
                    packSubInfoVO.setColthModel(map.get("colthModel"));
                    String imageUrl;
                    if (StringUtils.isBlank(map.get("picurl"))) {
                        imageUrl = "api/download/notfound.jpg";
                    } else {
                        imageUrl = new StringBuffer("api/download").append("/").append(map.get("picurl")).toString();
                    }
                    packSubInfoVO.setPicurl(imageUrl);
                    packSubInfoVOS.add(packSubInfoVO);
                }
            }
            packInfoVO.setPackSubInfoVOS(packSubInfoVOS);
            returnList.add(packInfoVO);
        }
        return returnList;
    }

    public PagedListHolder<OrderInfoVO> getWaitSettleList(OrderInfoVO orderInfoVO){
        List<Map<String,Object>> list = orderInfoDao.getWaitSettleList(orderInfoVO.getOrderNo(),orderInfoVO.getCustomerName());
        List<OrderInfoVO> returnList = new ArrayList<>();
        for (Map<String,Object> map : list){
            OrderInfoVO infoVO = new OrderInfoVO();
            infoVO.setOrderNo((Integer)map.get("orderNo"));
            infoVO.setCustomerNo((Integer)map.get("customerNo"));
            infoVO.setCustomerName((String)map.get("customerName"));
            infoVO.setOrderDate((Date)map.get("orderDate"));
            infoVO.setOrderAmount((Double)map.get("orderAmount"));
            returnList.add(infoVO);
        }
        PagedListHolder<OrderInfoVO> pagedListHolder = new PagedListHolder<>(returnList);
        pagedListHolder.setPage(orderInfoVO.getPageNum() - 1);
        pagedListHolder.setPageSize(orderInfoVO.getPageSize());
        return pagedListHolder;
    }

    public Integer getMaxOrderNo(){
        return orderInfoDao.findMaxOrderNo();
    }
}
