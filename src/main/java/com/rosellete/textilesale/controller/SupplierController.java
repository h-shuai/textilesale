package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.interfaces.SupplierApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.SupplierSaveVO;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import javax.validation.Valid;

@Data
@Log4j2
public class SupplierController implements SupplierApi {
    @Override
    public RestResponse createSupplier(@Valid SupplierSaveVO supplierSaveVO) {
        log.info("供应商信息保存");

        return null;
    }
}
