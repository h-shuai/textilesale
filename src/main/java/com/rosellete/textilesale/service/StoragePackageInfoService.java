package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.StoragePackageInfoDao;
import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.model.StoragePackage;
import com.rosellete.textilesale.model.SupplierInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StoragePackageInfoService {

    @Autowired
    private StoragePackageInfoDao storagePackageInfoDao;

    public void saveStoragePackageList(List<StoragePackage> packageList) {
        storagePackageInfoDao.saveAll(packageList);
    }

    public List<StoragePackage> findAllByRecordNo(String recordNo) {
        return storagePackageInfoDao.findByRecordNo(recordNo);
    }

    public List<Map<String, Object>> findPackageList(String storageType,Integer conszignorNo) {
        return storagePackageInfoDao.findPackageList(storageType,conszignorNo);
    }

    public long findPackageListSize(StoragePackage storagePackageInfo, String storageType,
                                    SupplierInfo supplierInfo, CustomerInfo customerInfo, Date startDate, Date endDate) {
        String consignor=null;
        String consignorPhone=null;
        String consignorType=null;
        String industryType=null;
        Integer conszignorNo=null;
        if (StringUtils.equals("1",storageType)){
            consignor=supplierInfo.getName();
            conszignorNo= supplierInfo.getSupplierNo();
            consignorPhone=supplierInfo.getPhone();
            consignorType=supplierInfo.getType();
            industryType=supplierInfo.getIndustry();
        }else if(StringUtils.equals("2",storageType)){
            consignor=customerInfo.getName();
            conszignorNo= customerInfo.getCustomerNo();
            consignorPhone=customerInfo.getPhone();
            consignorType=customerInfo.getType();
            industryType=customerInfo.getIndustry();
        }
        return storagePackageInfoDao.findByConsignorInfo(storagePackageInfo.getRecordNo(),
                storageType, conszignorNo,consignor, consignorPhone, consignorType,industryType, startDate, endDate).size();
    }

    public List<StoragePackage> findStoragePackageByPackageNo(String recordNo, String packageNo) {
        return storagePackageInfoDao.findByPackageNo(recordNo, packageNo);
    }

    public StoragePackage findFirstStoragePackage(String recordNo, String packageNo) {
        return storagePackageInfoDao.findByPackageNo(recordNo, packageNo).stream().findFirst().orElse(null);
    }
}
