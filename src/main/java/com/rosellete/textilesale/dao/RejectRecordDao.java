package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.RejectRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RejectRecordDao extends BaseRepository<RejectRecord, String> {
    @Query(value = "SELECT t.* FROM t_reject_record t where IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.supplier_no= ?2, 1 = 1)" +
            " and exists (select  1 from t_supplier_info t1 where t1.supplier_no=t.supplier_no" +
            " and IF(?3 is not null, t1.name like CONCAT(?3, '%'), 1 = 1)" +
            " and IF(?4 is not null, t1.type = ?4, 1 = 1)" +
            " and IF(?5 is not null, t1.industry = ?5, 1 = 1)" +
            " and IF(?6 is not null, t1.phone like CONCAT(?6, '%'), 1 = 1))", nativeQuery = true)
    List<RejectRecord> findBySupplierInfo(String recordNo,Integer supplierNo, String supplierName,
                                          String supplierType, String industryType,
                                          String supplierPhoneNo);

    @Query(value = "SELECT t.* FROM t_reject_record t where IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.supplier_no= ?2, 1 = 1)" +
            " and exists (select  1 from t_supplier_info t1 where t1.supplier_no=t.supplier_no" +
            " and IF(?3 is not null, t1.name like CONCAT(?3, '%'), 1 = 1)" +
            " and IF(?4 is not null, t1.type = ?4, 1 = 1)" +
            " and IF(?5 is not null, t1.industry = ?5, 1 = 1)" +
            " and IF(?6 is not null, t1.phone like CONCAT(?6, '%'), 1 = 1))limit ?7,?8", nativeQuery = true)
    List<RejectRecord> findPageBySupplierInfo(String recordNo,Integer supplierNo, String supplierName,
                                              String supplierType, String industryType,
                                              String supplierPhoneNo, @Param("startRow") Integer startRow, @Param("size") Integer size);
}
