package com.rosellete.textilesale.controller;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.CustomerBusiness;
import com.rosellete.textilesale.business.StorageBusiness;
import com.rosellete.textilesale.business.SupplierBusiness;
import com.rosellete.textilesale.interfaces.StorageApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/storage")
public class StorageController implements StorageApi {
    @Autowired
    private StorageBusiness storageBusiness;

    @Autowired
    private SupplierBusiness supplierBusiness;

    @Autowired
    private CustomerBusiness customerBusiness;

    @Override
    public RestResponse getStorageRecordList(@RequestBody StorageRecordVO storageRecordVO) {
        PageInfo<StorageRecordVO> pageInfo = storageBusiness.getStorageRecordList(storageRecordVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse saveStorageRecord(@RequestBody StorageRecordVO storageRecordVO) {
        RestResponse response = new RestResponse();
        try {
            storageBusiness.saveStorageRecord(storageRecordVO);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg(null==e.getMessage()?"系统内部错误，请稍后重试":e.getMessage());
            log.error("保存抄包记录数据{}失败", storageRecordVO, e);
        }
        return response;
    }

    @Override
    public RestResponse getStoragePackage(String recordNo) {
        PageInfo<StoragePackageVO> pageInfo = storageBusiness.getStoragePackage(recordNo);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getStoragePackageList(@RequestBody @Valid ConsignorVO consignorVO) {
        PageInfo<StoragePackageVO> pageInfo = storageBusiness.getStoragePackageList(consignorVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getPackageInventory(String recordNo, String packageNo) {
        PageInfo<PackageInventoryInfoVO> pageInfo = storageBusiness.getPackageInventory(recordNo, packageNo);
        return new RestResponse(pageInfo);
    }


    @Override
    public RestResponse getStoredInventory(Integer supplierNo) {
        PageInfo<ProductTypeInfoVO> pageInfo = storageBusiness.findStoredInventory(supplierNo);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse savePackageInventoryList(@Valid PackageInventorySaveVO packageInventorySaveVO) {
        RestResponse response = new RestResponse();
        try {
            storageBusiness.savePackageInventoryList(packageInventorySaveVO);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg(null==e.getMessage()?"系统内部错误，请稍后重试":e.getMessage());
            log.error("保存抄包记录数据{}失败", packageInventorySaveVO, e);
        }
        return response;
    }

    @Override
    public RestResponse getAllSupplierAndCustomer() {
        PageInfo<ConsignorVO> pageInfo = storageBusiness.findAllSupplierAndCustomerWarehouseRelated();
        return new RestResponse(pageInfo);
    }

    @Override
    public  RestResponse getAllCustomer() {
        PageInfo<CustomerInfoVO> pageInfo = customerBusiness.findAllCustomerWarehouseRelated();
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getAllSupplier() {
        PageInfo<SupplierInfoVO> pageInfo = supplierBusiness.findAllSupplierWarehouseRelated();
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getSupplier(@Valid SupplierInfoVO supplierInfoVO) {
        PageInfo<SupplierInfoVO> pageInfo = supplierBusiness.findSupplierListWarehouseRelated(supplierInfoVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getCustomer(@Valid CustomerInfoVO customerInfoVO) {
        PageInfo<CustomerInfoVO> pageInfo = customerBusiness.findCustomerListWarehouseRelated(customerInfoVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getSupplierAndCustomer(@Valid ConsignorVO consignorVO) {
        PageInfo<ConsignorVO> pageInfo = storageBusiness.findSupplierAndCustomerWarehouseRelated(consignorVO);
        return new RestResponse(pageInfo);
    }


    @Override
    public RestResponse getAllProductType() {
        PageInfo<ProductTypeInfoVO> pageInfo = storageBusiness.findAllProductType();
        return new RestResponse(pageInfo);
    }
}
