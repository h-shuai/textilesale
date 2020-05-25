package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.model.SupplierInfo;
import com.rosellete.textilesale.vo.SupplierInfoVO;
import org.springframework.data.domain.Page;

public interface SupplierBusiness {
    PageInfo<SupplierInfoVO> findAllSupplierWarehouseRelated();

    PageInfo<SupplierInfoVO> findAllSupplier();

    PageInfo<SupplierInfoVO> findSupplierListWarehouseRelated(SupplierInfoVO supplierInfoVO);

    Page<SupplierInfo> findSupplier(SupplierInfoVO supplierInfoVO);

    void save(SupplierInfoVO supplierInfoVO);

    SupplierInfo findBySupplierNo(int supplierNo);
}
