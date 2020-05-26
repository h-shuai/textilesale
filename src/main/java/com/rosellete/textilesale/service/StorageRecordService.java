package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.StorageRecordDao;
import com.rosellete.textilesale.model.CustomerInfo;
import com.rosellete.textilesale.model.StorageRecord;
import com.rosellete.textilesale.model.SupplierInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StorageRecordService {

    @Autowired
    private StorageRecordDao storageRecordDao;

    public List<StorageRecord> getStorageRecordList(StorageRecord storageRecord,SupplierInfo supplierInfo,
                                                    CustomerInfo customerInfo, Date startDate, Date endDate) {
        String consignor=null;
        String consignorPhone=null;
        String consignorType=null;
        String industryType=null;
        Integer conszignorNo=null;
        if (StringUtils.equals("1",storageRecord.getStorageType())){
            consignor=supplierInfo.getSupplierName();
            conszignorNo= supplierInfo.getSupplierNo();
            consignorPhone=supplierInfo.getSupplierPhoneNo();
            consignorType=supplierInfo.getSupplierType();
            industryType=supplierInfo.getIndustryType();
        }else if(StringUtils.equals("2",storageRecord.getStorageType())){
            consignor=customerInfo.getCustomerName();
            conszignorNo= customerInfo.getCustomerNo();
            consignorPhone=customerInfo.getCustomerPhoneNo();
            consignorType=customerInfo.getCustomerType();
            industryType=customerInfo.getIndustryType();
        }
        return storageRecordDao.findPagedByConsignorInfo(storageRecord.getRecordNo(), storageRecord.getStorageType(),
                conszignorNo,consignor, consignorPhone, consignorType,industryType, startDate, endDate,
                (storageRecord.getPageNum()-1)*storageRecord.getPageSize(),storageRecord.getPageSize());
    }

    public long getStorageRecordListSize(StorageRecord storageRecord, SupplierInfo supplierInfo,
                                         CustomerInfo customerInfo, Date startDate, Date endDate) {
        String consignor=null;
        String consignorPhone=null;
        String consignorType=null;
        String industryType=null;
        Integer conszignorNo=null;
        if (StringUtils.equals("1",storageRecord.getStorageType())){
            consignor=supplierInfo.getSupplierName();
            conszignorNo= supplierInfo.getSupplierNo();
            consignorPhone=supplierInfo.getSupplierPhoneNo();
            consignorType=supplierInfo.getSupplierType();
            industryType=supplierInfo.getIndustryType();
        }else if(StringUtils.equals("2",storageRecord.getStorageType())){
            consignor=customerInfo.getCustomerName();
            conszignorNo= customerInfo.getCustomerNo();
            consignorPhone=customerInfo.getCustomerPhoneNo();
            consignorType=customerInfo.getCustomerType();
            industryType=customerInfo.getIndustryType();
        }
        return storageRecordDao.findByConsignorInfo(storageRecord.getRecordNo(), storageRecord.getStorageType(),
                conszignorNo,consignor, consignorPhone, consignorType,industryType, startDate, endDate).size();
    }

    public void saveStorageRecord(StorageRecord storageRecord) {
        storageRecordDao.save(storageRecord);
    }

    public StorageRecord findByPrimaryKey(String recordNo) {
        Optional<StorageRecord> storageRecord = storageRecordDao.findById(recordNo);
        return storageRecord.orElse(null);
    }

    public List<Map<String, String>> findAllSupplierAndCustomer() {
        return storageRecordDao.findAllSupplierAndCustomer();
    }

    public List<Map<String, String>> findSupplierAndCustomer(String storageType, Integer consignorNo,String consignor,
                                                             String consignorPhoneNo, String consignorType,
                                                             String industryType,Integer pageNum, Integer pageSize) {
        return storageRecordDao.findPagedSupplierAndCustomer(storageType,consignorNo,consignor,consignorPhoneNo,consignorType,
                industryType,(pageNum-1)*pageSize,pageSize);

    }

    public long getSupplierAndCustomerListSize(String storageType, Integer consignorNo, String consignor,
                                               String consignorPhoneNo, String consignorType, String industryType) {

        return storageRecordDao.findSupplierAndCustomer(storageType,consignorNo,consignor,consignorPhoneNo,consignorType,
                industryType).size();
    }
}
