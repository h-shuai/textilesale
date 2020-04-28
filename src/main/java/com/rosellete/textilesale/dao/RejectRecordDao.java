package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.RejectRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RejectRecordDao extends BaseRepository<RejectRecord, String> {
    @Query(value = "SELECT t.* FROM t_reject_record t where IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.supplier_name like CONCAT(?2, '%'), 1 = 1)" +
            " and IF(?3 is not null, t.supplier_type = ?3, 1 = 1)" +
            " and IF(?4 is not null, t.industry_type = ?4, 1 = 1)" +
            " and IF(?5 is not null, t.supplier_phone_no like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t.reject_date >= ?6, 1 = 1)" +
            " and IF(?7 is not null, t.reject_date < ?7, 1 = 1)", nativeQuery = true)
    List<RejectRecord> findBySupplierInfo(String recordNo, String supplierName, String supplierType, String industryType,
                                          String supplierPhoneNo, Date startDate, Date endDate);
}
