package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.StoragePackageInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface StoragePackageInfoDao extends BaseRepository<StoragePackageInfo, Long> {

    @Query(value = "SELECT t.* FROM t_storage_package_info t where t.record_no= ?1 ", nativeQuery = true)
    List<StoragePackageInfo> findByRecordNo(String recordNo);

    @Query(value = "SELECT t1.package_no,t1.id,t2.* FROM t_storage_record t2, t_storage_package_info t1 where t1.record_no = t2.record_no" +
            " and IF(?1 is not null, t1.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t1.package_no like CONCAT(?2, '%'), 1 = 1)" +
            " and IF(?3 is not null, t2.storage_type= ?3, 1 = 1)" +
            " and IF(?4 is not null, t2.storage_clerk_name like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t2.consignor_phone_no like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t2.consignor like CONCAT(?6, '%'), 1 = 1)" +
            " and IF(?7 is not null, t2.consignor_type = ?7, 1 = 1)" +
            " and IF(?8 is not null, t2.industry_type = ?8, 1 = 1)" +
            " and IF(?9 is not null, t2.storage_date >= ?9, 1 = 1)" +
            " and IF(?10 is not null, t2.storage_date < ?10, 1 = 1)", nativeQuery = true)
    List<Map<String, Object>> findByConsignorInfo(String recordNo, String packageNo, String storageType,
                                                  String storageClerkName, String consignorPhoneNo, String consignor,
                                                  String consignorType, String industryType, Date startDate,
                                                  Date endDate);

    @Query(value = "SELECT t.* FROM t_storage_package_info t where t.record_no= ?1 and t.package_no = ?2 ", nativeQuery = true)
    List<StoragePackageInfo> findByPackageNo(String recordNo, String packageNo);
}
