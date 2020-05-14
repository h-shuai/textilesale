package com.rosellete.textilesale.controller;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.SupplierBusiness;
import com.rosellete.textilesale.interfaces.SupplierApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.OrderInfoVO;
import com.rosellete.textilesale.vo.SupplierInfoVO;
import com.rosellete.textilesale.vo.SupplierSaveVO;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Data
@Log4j2
@RestController
@RequestMapping("/api/supplier")
public class SupplierController implements SupplierApi {
    @Autowired
    private SupplierBusiness supplierBusiness;

    @Override
    public RestResponse createSupplier(@Valid SupplierSaveVO supplierSaveVO) {
        log.info("供应商信息保存");

        return null;
    }

    @Override
    public RestResponse getAllSupplier() {
        PageInfo<SupplierInfoVO> pageInfo = supplierBusiness.findAllSupplier();
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getSupplier(SupplierInfoVO supplierInfoVO) {
        PageInfo<SupplierInfoVO> pageInfo = supplierBusiness.findSupplier(supplierInfoVO);
        return new RestResponse(pageInfo);
    }
}
