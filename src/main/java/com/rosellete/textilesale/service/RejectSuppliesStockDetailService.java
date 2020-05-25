package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.RejectSuppliesStockDetailDao;
import com.rosellete.textilesale.model.RejectSuppliesStockDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RejectSuppliesStockDetailService {
    @Autowired
    private RejectSuppliesStockDetailDao rejectSuppliesStockDetailDao;

    public void saveAll(List<RejectSuppliesStockDetail> list) {
        rejectSuppliesStockDetailDao.saveAll(list);
    }

    public List<RejectSuppliesStockDetail> findAllByRecordNoAndType(String recordNo,String productType) {
        RejectSuppliesStockDetail detail = new RejectSuppliesStockDetail();
        detail.setRecordNo(recordNo);
        detail.setProductType(productType);
        Example<RejectSuppliesStockDetail> example = Example.of(detail);
        return rejectSuppliesStockDetailDao.findAll(example);
    }

    public List<RejectSuppliesStockDetail> findAllByRecordNo(String recordNo) {
        RejectSuppliesStockDetail detail = new RejectSuppliesStockDetail();
        detail.setRecordNo(recordNo);
        Example<RejectSuppliesStockDetail> example = Example.of(detail);
        return rejectSuppliesStockDetailDao.findAll(example);
    }

    public void deleteAll(List<RejectSuppliesStockDetail> existedStockDetailList) {
        rejectSuppliesStockDetailDao.deleteAll(existedStockDetailList);
    }

    public List<Map<String, Object>> getPieceList(String recordNo, String productType) {
        return rejectSuppliesStockDetailDao.getPieceList(recordNo, productType);
    }

    public RejectSuppliesStockDetail getById(Long id){
        return rejectSuppliesStockDetailDao.getById(id);
    }

    public void updateStatusByIds(String status, List<String> ids) {
        rejectSuppliesStockDetailDao.updateStatusByIds(status, ids);
    }
}
