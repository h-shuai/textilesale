package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.CustomerDao;
import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.model.SupplierInfo;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.vo.CustomerInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<Map<String, String>> findAllOrderByVip() {
        return customerDao.findAllOrderByVip();
    }

    public Page<CustomerInfo> findCustomerList(CustomerInfo customerInfo) {
        Example<CustomerInfo> example = Example.of(customerInfo);
        Sort sort = Sort.by(Sort.Direction.DESC, "vip");
        Pageable pageable = PageRequest.of(customerInfo.getPageNum() - 1, customerInfo.getPageSize(), sort);
        return customerDao.findAll(example, pageable);
    }

    public CustomerInfo findByPrimaryKey(Integer customerNo) {
        return customerDao.findById(customerNo).orElse(null);
    }

    public void save(CustomerInfoVO customerInfoVO){
        String[] nullOrBlankPropertyNames = NullPropertiesUtil.getNullOrBlankPropertyNames(customerInfoVO);
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(customerInfoVO, customerInfo, nullOrBlankPropertyNames);

        customerDao.save(customerInfo);
    }
}
