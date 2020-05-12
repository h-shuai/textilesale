package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.OrderSaveVO;
import com.rosellete.textilesale.vo.SupplierSaveVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface SupplierApi {
    @PostMapping(value = "/createSupplier", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse createSupplier(@RequestBody @Valid SupplierSaveVO supplierSaveVO);

}
