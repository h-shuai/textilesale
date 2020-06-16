package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.StorageRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface StorageRecordDao extends BaseRepository<StorageRecord, Integer> {

    @Query(value = "SELECT t.* FROM t_storage_record t where IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.storage_type = ?2, 1 = 1)" +
            " and IF(?3 is not null, t.consignor_no = ?3, 1 = 1)" +
            " and (exists( select 1 from t_supplier_info t1 where t1.supplier_no= t.consignor_no" +
            " and IF(?4 is not null, t1.name like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t1.phone like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t1.type = ?6, 1 = 1)" +
            " and IF(?7 is not null, t1.industry = ?7, 1 = 1)) or" +
            " exists( select 1 from t_customer_info t2 where t2.customer_no= t.consignor_no" +
            " and IF(?4 is not null, t2.name like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t2.phone like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t2.type = ?6, 1 = 1)" +
            " and IF(?7 is not null, t2.industry = ?7, 1 = 1)))" +
            " and IF(?8 is not null, t.storage_date >= ?8, 1 = 1)" +
            " and IF(?9 is not null, t.storage_date < ?9, 1 = 1)", nativeQuery = true)
    List<StorageRecord> findByConsignorInfo(Integer recordNo, String storageType, Integer conszignorNo, String consignor, String consignorPhoneNo,
                                            String consignorType, String industryType,
                                            @Temporal(TemporalType.TIMESTAMP) Date startDate,
                                            @Temporal(TemporalType.TIMESTAMP) Date endDate);

    @Query(value = "SELECT t.* FROM t_storage_record t where IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.storage_type = ?2, 1 = 1)" +
            " and IF(?3 is not null, t.consignor_no = ?3, 1 = 1)" +
            " and (exists( select 1 from t_supplier_info t1 where t1.supplier_no= t.consignor_no" +
            " and IF(?4 is not null, t1.name like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t1.phone like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t1.type = ?6, 1 = 1)" +
            " and IF(?7 is not null, t1.industry = ?7, 1 = 1)) or" +
            " exists( select 1 from t_customer_info t2 where t2.customer_no= t.consignor_no" +
            " and IF(?4 is not null, t2.name like CONCAT(?4, '%'), 1 = 1)" +
            " and IF(?5 is not null, t2.phone like CONCAT(?5, '%'), 1 = 1)" +
            " and IF(?6 is not null, t2.type = ?6, 1 = 1)" +
            " and IF(?7 is not null, t2.industry = ?7, 1 = 1)))" +
            " and IF(?8 is not null, t.storage_date >= ?8, 1 = 1)" +
            " and IF(?9 is not null, t.storage_date < ?9, 1 = 1) limit ?10,?11", nativeQuery = true)
    List<StorageRecord> findPagedByConsignorInfo(Integer recordNo, String storageType, Integer conszignorNo, String consignor,
                                                 String consignorPhoneNo,
                                                 String consignorType, String industryType,
                                                 @Temporal(TemporalType.TIMESTAMP) Date startDate,
                                                 @Temporal(TemporalType.TIMESTAMP) Date endDate,
                                                 @Param("startRow") Integer startRow, @Param("size") Integer size);

    @Query(value = "SELECT * from( " +
            "            SELECT a.supplier_no AS consignorNo,a.NAME AS consignor, a.phone AS consignorPhoneNo, '1' AS storageType, a.type AS consignorType," +
            "                   a.industry AS industryType, a.address AS consignorAddress, a.vip AS priority " +
            "            FROM  t_supplier_info a" +
            "            union all" +
            "            SELECT b.customer_no AS consignorNo,b.NAME AS consignor, b.phone AS consignorPhoneNo, '2' AS storageType, b.type AS consignorType," +
            "                   b.industry AS industryType, b.address AS consignorAddress, b.vip AS priority " +
            "            FROM t_customer_info b" +
            ") t ORDER BY t.priority", nativeQuery = true)
    List<Map<String, String>> findAllSupplierAndCustomer();

    @Query(value = "SELECT * from( " +
            "            SELECT a.supplier_no AS consignorNo,a.NAME AS consignor, a.phone AS consignorPhoneNo, '1' AS storageType, a.type AS consignorType," +
            "                   a.industry AS industryType, a.address AS consignorAddress, a.vip AS priority " +
            "            FROM  t_supplier_info a where IF(?1 is not null, ?1 = '1', 1 = 1 ) and IF(?2 is not null, a.supplier_no = ?2, 1 = 1)" +
            "                   and IF(?3 is not null, a.name like CONCAT(?3, '%'), 1 = 1)" +
            "                   and IF(?4 is not null, a.phone like CONCAT(?4, '%'), 1 = 1) and IF(?5 is not null, a.type = ?5, 1 = 1)" +
            "                   and IF(?6 is not null, a.industry = ?6, 1 = 1)" +
            "            union all" +
            "            SELECT b.customer_no AS consignorNo,b.NAME AS consignor, b.phone AS consignorPhoneNo, '2' AS storageType, b.type AS consignorType," +
            "                   b.industry AS industryType, b.address AS consignorAddress, b.vip AS priority " +
            "            FROM t_customer_info b where IF(?1 is not null, ?1 = '2', 1 = 1 ) and IF(?2 is not null, b.customer_no = ?2, 1 = 1) " +
            "                   and IF(?3 is not null, b.name like CONCAT(?3, '%'), 1 = 1)" +
            "                   and IF(?4 is not null, b.phone like CONCAT(?4, '%'), 1 = 1) and IF(?5 is not null, b.type = ?5, 1 = 1)" +
            "                   and IF(?6 is not null, b.industry = ?6, 1 = 1)" +
            "      ) t where exists (select  1 from t_storage_record c,t_storage_package_info d " +
            "                  where c.storage_type=t.storageType and c.consignor_no = t.consignorNo " +
            "                   and c.record_no=d.record_no and d.package_status = '0') ORDER BY t.priority limit ?7,?8", nativeQuery = true)
    List<Map<String, String>> findPagedSupplierAndCustomer(String storageType, Integer consignorNo, String consignor,
                                                           String consignorPhoneNo, String consignorType, String industryType,
                                                           @Param("startRow") Integer startRow, @Param("size") Integer size);

    @Query(value = "SELECT * from( " +
            "            SELECT a.supplier_no AS consignorNo,a.NAME AS consignor, a.phone AS consignorPhoneNo, '1' AS storageType, a.type AS consignorType," +
            "                   a.industry AS industryType, a.address AS consignorAddress, a.vip AS priority " +
            "            FROM  t_supplier_info a where IF(?1 is not null, ?1 = '1', 1 = 1 ) and IF(?2 is not null, a.supplier_no = ?2, 1 = 1)" +
            "                   and IF(?3 is not null, a.name like CONCAT(?3, '%'), 1 = 1)" +
            "                   and IF(?4 is not null, a.phone like CONCAT(?4, '%'), 1 = 1) and IF(?5 is not null, a.type = ?5, 1 = 1)" +
            "                   and IF(?6 is not null, a.industry = ?6, 1 = 1)" +
            "            union all" +
            "            SELECT b.customer_no AS consignorNo,b.NAME AS consignor, b.phone AS consignorPhoneNo, '2' AS storageType, b.type AS consignorType," +
            "                   b.industry AS industryType, b.address AS consignorAddress, b.vip AS priority " +
            "            FROM t_customer_info b where IF(?1 is not null, ?1 = '2', 1 = 1 ) and IF(?2 is not null, b.customer_no = ?2, 1 = 1) " +
            "                   and IF(?3 is not null, b.name like CONCAT(?3, '%'), 1 = 1)" +
            "                   and IF(?4 is not null, b.phone like CONCAT(?4, '%'), 1 = 1) and IF(?5 is not null, b.type = ?5, 1 = 1)" +
            "                   and IF(?6 is not null, b.industry = ?6, 1 = 1)" +
            "      ) t where exists (select  1 from t_storage_record c,t_storage_package_info d " +
            "                  where c.storage_type=t.storageType and c.consignor_no = t.consignorNo " +
            "                   and c.record_no=d.record_no and d.package_status = '0') ORDER BY t.priority", nativeQuery = true)
    List<Map<String, String>> findSupplierAndCustomer(String storageType, Integer consignorNo, String consignor,
                                                      String consignorPhoneNo, String consignorType, String industryType);

    @Query(value = "select ifnull(max(a.record_no),100000) as recordNo from t_storage_record a ", nativeQuery = true)
    int findMaxRecordNo();

}
