package com.rosellete.textilesale.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.CustomerBusiness;
import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.service.CustomerService;
import com.rosellete.textilesale.util.SupplierAndCustomerConvertorUtil;
import com.rosellete.textilesale.vo.CustomerInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("customerBusiness")
public class CustomerBusinessImpl implements CustomerBusiness {
    @Autowired
    private CustomerService customerService;

    @Override
    public PageInfo<CustomerInfoVO> findAllCustomerWarehouseRelated() {
        List<Map<String, String>> allOrderByVip = customerService.findAllOrderByVip();
        List<CustomerInfoVO> collect = allOrderByVip.stream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, CustomerInfoVO.class);
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }

    @Override
    public PageInfo<CustomerInfoVO> findCustomerListWarehouseRelated(CustomerInfoVO customerInfoVO) {
        CustomerInfo customerInfo = new CustomerInfo();
        SupplierAndCustomerConvertorUtil.convertCustomerVO2Info(customerInfoVO, customerInfo);
        List<CustomerInfo> customerInfoList = customerService.findCustomerList(customerInfo);
        List<CustomerInfoVO> collect = customerInfoList.stream().map(e -> {
            CustomerInfoVO temp = new CustomerInfoVO();
            SupplierAndCustomerConvertorUtil.convertCustomerInfo2VO(e, temp);
            return temp;
        }).collect(Collectors.toList());
        return new PageInfo<>(collect);
    }


}
