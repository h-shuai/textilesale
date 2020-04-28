package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.StoragePackageInfoDao;
import com.rosellete.textilesale.model.StoragePackageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StoragePackageInfoService {

    @Autowired
    private StoragePackageInfoDao storagePackageInfoDao;

    public void saveStoragePackageList(List<StoragePackageInfo> packageList) {
        storagePackageInfoDao.saveAll(packageList);
    }

    public List<StoragePackageInfo> findAllByRecordNo(String recordNo) {
        return storagePackageInfoDao.findByRecordNo(recordNo);
    }

    public List<Map<String, Object>> findPackageList(String recordNo, String packageNo, String storageType, String storageClerkName,
                                                     String consignorPhoneNo, String consignor, String consignorType,
                                                     String industryType, Date startDate, Date endDate) {
        return storagePackageInfoDao.findByConsignorInfo(recordNo, packageNo, storageType, storageClerkName,
                consignorPhoneNo, consignor, consignorType, industryType, startDate, endDate);
    }

    public List<StoragePackageInfo> findStoragePackageByPackageNo(String recordNo, String packageNo) {
        return storagePackageInfoDao.findByPackageNo(recordNo,packageNo);
    }
}
