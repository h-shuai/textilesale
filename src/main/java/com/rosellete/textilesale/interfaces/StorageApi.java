package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface StorageApi {
    @PostMapping(value = "/queryStorageRecordList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getStorageRecordList(@RequestBody @Valid StorageRecordVO storageRecordVO);

    @PostMapping(value = "/saveStorageRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveStorageRecord(@RequestBody @Valid StorageRecordVO storageRecordVO);

    @GetMapping(path = "/viewStorageDetail")
    RestResponse getStoragePackage(@RequestParam("recordNo") Integer recordNo);

    @PostMapping(value = "/queryStoragePackageList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getStoragePackageList(@RequestBody @Valid ConsignorVO consignorVO);

    @GetMapping(path = "/viewPackageInventory")
    RestResponse getPackageInventory(@RequestParam("recordNo") Integer recordNo,
                                     @RequestParam("packageNo") String packageNo);

    @GetMapping(path = "/getStoredInventory")
    RestResponse getStoredInventory(@RequestParam("supplierNo") Integer supplierNo);

    @PostMapping(value = "/savePackageInventory", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse savePackageInventoryList(@RequestBody @Valid PackageInventorySaveVO packageInventorySaveVO);


    @GetMapping(path = "/getAllSupplierAndCustomer")
    RestResponse getAllSupplierAndCustomer();

    @GetMapping(path = "/getAllSupplier")
    RestResponse getAllSupplier();

    @GetMapping(path = "/getAllCustomer")
    RestResponse getAllCustomer();

    @PostMapping(value = "/getCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getCustomer(@RequestBody @Valid CustomerInfoVO customerInfoVO);

    @PostMapping(value = "/getSupplier", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getSupplier(@RequestBody @Valid SupplierInfoVO supplierInfoVO);

    @PostMapping(value = "/getSupplierAndCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getSupplierAndCustomer(@RequestBody @Valid ConsignorVO consignorVO);

    @GetMapping(path = "/getAllProductType")
    RestResponse getAllProductType();

}
