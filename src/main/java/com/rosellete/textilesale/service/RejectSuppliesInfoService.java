package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.RejectSuppliesInfoDao;
import com.rosellete.textilesale.model.RejectSuppliesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RejectSuppliesInfoService {

    @Autowired
    private RejectSuppliesInfoDao rejectSuppliesInfoDao;

    public void saverejectSupplies(List<RejectSuppliesInfo> list) {
        rejectSuppliesInfoDao.saveAll(list);
    }

    public List<RejectSuppliesInfo> findRecordDetailByRecordNo(String recordNo) {
        return rejectSuppliesInfoDao.findAllByRecordNo(recordNo);
    }
}
