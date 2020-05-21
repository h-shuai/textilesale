package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.RejectSuppliesInfoDao;
import com.rosellete.textilesale.model.RejectSupplies;
import com.rosellete.textilesale.model.SupplierInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RejectSuppliesInfoService {

    @Autowired
    private RejectSuppliesInfoDao rejectSuppliesInfoDao;

    public void saveRejectSupplies(List<RejectSupplies> list) {
        rejectSuppliesInfoDao.saveAll(list);
    }

    public List<RejectSupplies> findRecordDetailByRecordNo(String recordNo) {
        return rejectSuppliesInfoDao.findAllByRecordNo(recordNo);
    }

    public List<RejectSupplies> findRejectSuppliesDetails(RejectSupplies rejectSuppliesInfo, SupplierInfo supplierInfo) {
        return rejectSuppliesInfoDao.findRejectSuppliesDetails(rejectSuppliesInfo.getRecordNo(), supplierInfo.getSupplierNo(), supplierInfo.getName(), supplierInfo.getType(),
                supplierInfo.getIndustry(), supplierInfo.getPhone(), (rejectSuppliesInfo.getPageNum() - 1) * rejectSuppliesInfo.getPageSize(), rejectSuppliesInfo.getPageSize());
    }

    public void deleteAll(List<RejectSupplies> existedRejectSuppliesList) {
        rejectSuppliesInfoDao.deleteAll(existedRejectSuppliesList);
    }
}
