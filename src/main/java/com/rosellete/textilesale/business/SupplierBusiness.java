package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.vo.SupplierInfoVO;

public interface SupplierBusiness {
    PageInfo<SupplierInfoVO> findAllSupplierWarehouseRelated();


    PageInfo<SupplierInfoVO> findAllSupplier();

    PageInfo<SupplierInfoVO> findSupplierListWarehouseRelated(SupplierInfoVO supplierInfoVO);

    PageInfo<SupplierInfoVO> findSupplier(SupplierInfoVO supplierInfoVO);
}
