package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.vo.PackageInventoryInfoVO;
import com.rosellete.textilesale.vo.StoragePackageVO;
import com.rosellete.textilesale.vo.StorageRecordVO;

public interface StorageBusiness {
    PageInfo<StorageRecordVO> getStorageRecordList(StorageRecordVO storageRecordVO);

    void saveStorageRecord(StorageRecordVO storageRecordVO);

    PageInfo<StoragePackageVO> getStoragePackage(String recordNo);

    PageInfo<StoragePackageVO> getStoragePackageList(StoragePackageVO storagePackageVO);

    PageInfo<PackageInventoryInfoVO> getPackageInventory(String recordNo, String packageNo);

    void savePackageInventoryList(StoragePackageVO storagePackageVO);
}
