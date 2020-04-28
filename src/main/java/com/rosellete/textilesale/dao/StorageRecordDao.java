package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.StorageRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public interface StorageRecordDao extends BaseRepository<StorageRecord, String> {

    @Query(value = "SELECT t.* FROM t_storage_record t where IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.storage_type = ?2, 1 = 1)" +
            " and IF(?3 is not null, t.consignor like CONCAT(?3, '%'), 1 = 1)" +
            " and IF(?4 is not null, t.consignor_phone_no like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t.consignor_type = ?5, 1 = 1)" +
            " and IF(?6 is not null, t.industry_type = ?6, 1 = 1) and IF(?7 is not null, t.storage_date >= ?7, 1 = 1)" +
            " and IF(?8 is not null, t.storage_date < ?8, 1 = 1)", nativeQuery = true)
    List<StorageRecord> findByConsignorInfo(String recordNo,String storageType, String consignor, String consignorPhoneNo,
                                            String consignorType, String industryType,
                                            @Temporal(TemporalType.TIMESTAMP) Date startDate,
                                            @Temporal(TemporalType.TIMESTAMP) Date endDate);
}
