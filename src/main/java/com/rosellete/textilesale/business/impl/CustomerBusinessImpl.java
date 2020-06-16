package com.rosellete.textilesale.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.CustomerBusiness;
import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.model.SupplierInfo;
import com.rosellete.textilesale.service.CustomerService;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.util.SupplierAndCustomerConvertorUtil;
import com.rosellete.textilesale.vo.CustomerInfoVO;
import com.rosellete.textilesale.vo.SupplierInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        List<CustomerInfoVO> collect = allOrderByVip.parallelStream().map(e -> {
            String jsonString = JSON.toJSONString(e);
            return JSONObject.parseObject(jsonString, CustomerInfoVO.class);
        }).collect(Collectors.toList());
        return new PageInfo(collect);
    }

    @Override
    public PageInfo<CustomerInfoVO> findCustomerListWarehouseRelated(CustomerInfoVO customerInfoVO) {
        String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(customerInfoVO);
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(customerInfoVO, customerInfo, nullOrBlankPropertyNames);
        Page<CustomerInfo> customerInfoList = customerService.findCustomerList(customerInfo);
        List<CustomerInfoVO> collect = customerInfoList.toList().parallelStream().map(e -> {
            CustomerInfoVO temp = new CustomerInfoVO();
            BeanUtils.copyProperties(e,temp);
            return temp;
        }).collect(Collectors.toList());
        com.github.pagehelper.Page page = new com.github.pagehelper.Page(customerInfoVO.getPageNum(), customerInfoVO.getPageSize());
        page.setTotal(customerInfoList.getTotalElements());
        page.addAll(collect);
        return new PageInfo<>(page);
    }

    @Override
    public void save(CustomerInfoVO customerInfo) {
        customerService.save(customerInfo);
    }

    @Override
    public Page<CustomerInfo> findAllCustomer(CustomerInfoVO customerInfoVO) {
        String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(customerInfoVO);
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(customerInfoVO, customerInfo, nullOrBlankPropertyNames);
        return customerService.findCustomerList(customerInfo);
    }

    @Override
    public CustomerInfo findByCustomerNo(int customerNo) {
        return customerService.findByPrimaryKey(customerNo);
    }
}
