package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.RejectSuppliesInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RejectSuppliesInfoDao extends BaseRepository<RejectSuppliesInfo, Long> {
    @Query(value = "SELECT t.* FROM t_reject_supplies_info t where t.record_no= ?1", nativeQuery = true)
    List<RejectSuppliesInfo> findAllByRecordNo(String recordNo);
}
