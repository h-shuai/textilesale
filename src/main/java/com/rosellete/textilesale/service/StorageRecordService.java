package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.StorageRecordDao;
import com.rosellete.textilesale.model.StorageRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StorageRecordService {

    @Autowired
    private StorageRecordDao storageRecordDao;

    public List<StorageRecord> getStorageRecordList(StorageRecord storageRecord, Date startDate, Date endDate) {
        return storageRecordDao.findByConsignorInfo(storageRecord.getRecordNo(),storageRecord.getStorageType(),
                storageRecord.getConsignor(),storageRecord.getConsignorPhoneNo(), storageRecord.getConsignorType(),
                storageRecord.getIndustryType(), startDate, endDate);
    }

    public void saveStorageRecord(StorageRecord storageRecord) {
        storageRecordDao.save(storageRecord);
    }

    public StorageRecord findByPrimaryKey(String recordNo) {
        Optional<StorageRecord> storageRecord = storageRecordDao.findById(recordNo);
        return storageRecord.isPresent() ? storageRecord.get() : null;
    }
}
