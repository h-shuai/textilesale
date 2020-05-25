package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.vo.CustomerInfoVO;
import org.springframework.data.domain.Page;

public interface CustomerBusiness {
    PageInfo<CustomerInfo> findAllCustomerWarehouseRelated();

    PageInfo<CustomerInfo> findCustomerListWarehouseRelated(CustomerInfoVO customerInfoVO);

    void save(CustomerInfoVO customerInfo);

    Page<CustomerInfo> findAllCustomer(CustomerInfoVO customerInfoVO);

    CustomerInfo findByCustomerNo(int customerNo);
}
