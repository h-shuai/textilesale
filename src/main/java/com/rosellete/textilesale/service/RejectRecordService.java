package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.RejectRecordDao;
import com.rosellete.textilesale.model.RejectRecord;
import com.rosellete.textilesale.model.SupplierInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RejectRecordService {

    @Autowired
    private RejectRecordDao rejectRecordDao;

    public List<RejectRecord> findRecordList(RejectRecord rejectRecord, SupplierInfo supplierInfo) {
        return rejectRecordDao.findPageBySupplierInfo(rejectRecord.getRecordNo(), supplierInfo.getSupplierNo(), supplierInfo.getName(), supplierInfo.getType(),
                supplierInfo.getIndustry(), supplierInfo.getPhone(), (rejectRecord.getPageNum() - 1) * rejectRecord.getPageSize(), rejectRecord.getPageSize());
    }

    public long findRecordListSize(RejectRecord rejectRecord, SupplierInfo supplierInfo) {
        return rejectRecordDao.findBySupplierInfo(rejectRecord.getRecordNo(), supplierInfo.getSupplierNo(), supplierInfo.getName(), supplierInfo.getType(),
                supplierInfo.getIndustry(), supplierInfo.getPhone()).size();
    }

    public void saveRejectRecord(RejectRecord rejectRecord) {
        rejectRecordDao.save(rejectRecord);
    }

    public RejectRecord findByPrimaryKey(String recordNo) {
        Optional<RejectRecord> optional = rejectRecordDao.findById(recordNo);
        return optional.orElse(null);
    }
}
