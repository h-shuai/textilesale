package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.CustomerDao;
import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.model.SupplierInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
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

    public List<CustomerInfo> findCustomerList(CustomerInfo customerInfo) {
        Example<CustomerInfo> example = Example.of(customerInfo);
        return customerDao.findAll(example, Sort.by("vip"));
    }

    public CustomerInfo findByPrimaryKey(Integer customerNo) {
        return customerDao.findById(customerNo).orElse(null);
    }
}
