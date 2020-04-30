package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.RejectRecordDao;
import com.rosellete.textilesale.model.RejectRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RejectRecordService {

    @Autowired
    private RejectRecordDao rejectRecordDao;

    public List<RejectRecord> findRecordList(RejectRecord rejectRecord, Date startDate, Date endDate) {
        return rejectRecordDao.findPageBySupplierInfo(rejectRecord.getRecordNo(), rejectRecord.getSupplierName(), rejectRecord.getSupplierType(),
                rejectRecord.getIndustryType(), rejectRecord.getSupplierPhoneNo(), startDate, endDate,
                (rejectRecord.getPageNum() - 1) * rejectRecord.getPageSize(), rejectRecord.getPageSize());
    }

    public long findRecordListSize(RejectRecord rejectRecord, Date startDate, Date endDate) {
        return rejectRecordDao.findBySupplierInfo(rejectRecord.getRecordNo(), rejectRecord.getSupplierName(), rejectRecord.getSupplierType(),
                rejectRecord.getIndustryType(), rejectRecord.getSupplierPhoneNo(), startDate, endDate).size();
    }

    public void saveRejectRecord(RejectRecord rejectRecord) {
        rejectRecordDao.save(rejectRecord);
    }

    public RejectRecord findByPrimaryKey(String recordNo) {
        Optional<RejectRecord> optional = rejectRecordDao.findById(recordNo);
        return optional.orElse(null);
    }
}
