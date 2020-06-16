package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.vo.*;

public interface StorageBusiness {
    PageInfo<StorageRecordVO> getStorageRecordList(StorageRecordVO storageRecordVO);

    void saveStorageRecord(StorageRecordVO storageRecordVO);

    PageInfo<StoragePackageVO> getStoragePackage(Integer recordNo);

    PageInfo<StoragePackageVO> getStoragePackageList(ConsignorVO consignorVO);

    PageInfo<PackageInventoryVO> getPackageInventory(Integer recordNo, String packageNo);

    void savePackageInventoryList(PackageInventorySaveVO packageInventorySaveVO);

    PageInfo<ConsignorVO> findAllSupplierAndCustomerWarehouseRelated();

    PageInfo<ConsignorVO> findSupplierAndCustomerWarehouseRelated(ConsignorVO consignorVO);

    PageInfo<ProductTypeVO> findAllProductType();

    PageInfo<ProductTypeVO> findStoredInventory(Integer supplierNo);

    Integer getSequenceNo();
}
