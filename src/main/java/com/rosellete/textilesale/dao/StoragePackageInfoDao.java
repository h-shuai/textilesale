package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.StoragePackageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface StoragePackageInfoDao extends BaseRepository<StoragePackageInfo, Long> {

    @Query(value = "SELECT t.* FROM t_storage_package_info t where t.record_no= ?1 ", nativeQuery = true)
    List<StoragePackageInfo> findByRecordNo(String recordNo);

    @Query(value = "SELECT t0.* FROM t_storage_record t, t_storage_package_info t0 where t0.record_no = t.record_no" +
            " and IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.storage_type = ?2, 1 = 1)" +
            " and IF(?3 is not null, t.consignor_no = ?3, 1 = 1)" +
            " and (exists( select 1 from t_supplier_info t1 where t1.supplier_no= t.consignor_no"+
            " and IF(?4 is not null, t1.name like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t1.phone like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t1.type = ?6, 1 = 1)" +
            " and IF(?7 is not null, t1.industry = ?7, 1 = 1)) or" +
            " exists( select 1 from t_customer_info t2 where t2.customer_no= t.consignor_no"+
            " and IF(?4 is not null, t2.name like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t2.phone like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t2.type = ?6, 1 = 1)" +
            " and IF(?7 is not null, t2.industry = ?7, 1 = 1)))" +
            " and IF(?8 is not null, t.storage_date >= ?8, 1 = 1)" +
            " and IF(?9 is not null, t.storage_date < ?9, 1 = 1)", nativeQuery = true)
    List<Map<String, Object>> findByConsignorInfo(String recordNo, String storageType,Integer conszignorNo, String consignor, String consignorPhoneNo,
                                                  String consignorType, String industryType,
                                                  @Temporal(TemporalType.TIMESTAMP) Date startDate,
                                                  @Temporal(TemporalType.TIMESTAMP) Date endDate);


    @Query(value = "SELECT t0.* FROM t_storage_record t, t_storage_package_info t0 where t0.record_no = t.record_no" +
            " and IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.storage_type = ?2, 1 = 1)" +
            " and IF(?3 is not null, t.consignor_no = ?3, 1 = 1)" +
            " and (exists( select 1 from t_supplier_info t1 where t1.supplier_no= t.consignor_no"+
            " and IF(?4 is not null, t1.name like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t1.phone like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t1.type = ?6, 1 = 1)" +
            " and IF(?7 is not null, t1.industry = ?7, 1 = 1)) or" +
            " exists( select 1 from t_customer_info t2 where t2.customer_no= t.consignor_no"+
            " and IF(?4 is not null, t2.name like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t2.phone like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t2.type = ?6, 1 = 1)" +
            " and IF(?7 is not null, t2.industry = ?7, 1 = 1)))" +
            " and IF(?8 is not null, t.storage_date >= ?8, 1 = 1)" +
            " and IF(?9 is not null, t.storage_date < ?9, 1 = 1) limit ?10,?11", nativeQuery = true)
    List<Map<String, Object>> findPageByConsignorInfo(String recordNo, String storageType,Integer conszignorNo, String consignor, String consignorPhoneNo,
                                                      String consignorType, String industryType,
                                                      @Temporal(TemporalType.TIMESTAMP) Date startDate,
                                                      @Temporal(TemporalType.TIMESTAMP) Date endDate, @Param("startRow") Integer startRow, @Param("size") Integer size);


    @Query(value = "SELECT t.* FROM t_storage_package_info t where t.record_no= ?1 and t.package_no = ?2 ", nativeQuery = true)
    List<StoragePackageInfo> findByPackageNo(String recordNo, String packageNo);

    @Query(value = "SELECT b.consignor_no,a.* FROM t_storage_package_info a,t_storage_record b where a.record_no =b.record_no and a.package_status='0' and b.storage_type=?1 and b.consignor_no =?2 ", nativeQuery = true)
    List<Map<String, Object>> findPackageList(String storageType, Integer conszignorNo);
}
