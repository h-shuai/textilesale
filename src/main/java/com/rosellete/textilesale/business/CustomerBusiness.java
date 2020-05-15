package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.vo.ConsignorVO;
import com.rosellete.textilesale.vo.CustomerInfoVO;

public interface CustomerBusiness {
    PageInfo<CustomerInfoVO> findAllCustomerWarehouseRelated();

    PageInfo<CustomerInfoVO> findCustomerListWarehouseRelated(CustomerInfoVO customerInfoVO);
}
