package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.RejectRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RejectRecordDao extends BaseRepository<RejectRecord, String> {
    @Query(value = "SELECT t.* FROM t_reject_record t where IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.supplier_no= ?2, 1 = 1)" +
            " and exists (select  1 from t_supplier_info t1 where t1.supplier_no=t.supplier_no" +
            " and IF(?3 is not null, t1.name like CONCAT(?3, '%'), 1 = 1)" +
            " and IF(?4 is not null, t1.type = ?4, 1 = 1)" +
            " and IF(?5 is not null, t1.industry = ?5, 1 = 1)" +
            " and IF(?6 is not null, t1.phone like CONCAT(?6, '%'), 1 = 1)) order by t.reject_date desc,t.record_no desc", nativeQuery = true)
    List<RejectRecord> findBySupplierInfo(String recordNo,Integer supplierNo, String supplierName,
                                          String supplierType, String industryType,
                                          String supplierPhoneNo);

    @Query(value = "SELECT t.* FROM t_reject_record t where IF(?1 is not null, t.record_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.supplier_no= ?2, 1 = 1)" +
            " and exists (select  1 from t_supplier_info t1 where t1.supplier_no=t.supplier_no" +
            " and IF(?3 is not null, t1.name like CONCAT(?3, '%'), 1 = 1)" +
            " and IF(?4 is not null, t1.type = ?4, 1 = 1)" +
            " and IF(?5 is not null, t1.industry = ?5, 1 = 1)" +
            " and IF(?6 is not null, t1.phone like CONCAT(?6, '%'), 1 = 1)) order by t.reject_date desc,t.record_no desc limit ?7,?8", nativeQuery = true)
    List<RejectRecord> findPageBySupplierInfo(String recordNo,Integer supplierNo, String supplierName,
                                              String supplierType, String industryType,
                                              String supplierPhoneNo, @Param("startRow") Integer startRow, @Param("size") Integer size);

    @Query(value = "select a.supplier_no customerNo,b.name customerName,count(*) orderCount from t_reject_record a,t_supplier_info b where IF(?1 is not null, b.name like CONCAT('%', ?1, '%'), 1 = 1) and a.supplier_no=b.supplier_no group by a.supplier_no", nativeQuery = true)
    List<Map<String, Object>> getWaitPackSupplier(@Param("supplierName") String supplierName);

    @Query(value = "select a.record_no orderNo,a.supplier_no customerNo,c.name customerName,sum(b.product_length) productLength " +
            "from t_reject_record a,t_reject_supplies_info b,t_supplier_info c where a.record_no=b.record_no " +
            "and IF(?1 is not null, a.record_no=?1, 1=1) and IF(?2 is not null, a.supplier_no = ?2, 1=1) and a.supplier_no=c.supplier_no " +
            "group by a.record_no,a.supplier_no order by a.record_no desc", nativeQuery = true)
    List<Map<String, Object>> getWaitPackRejectList(@Param("rejectNo") String rejectNo, @Param("supplierNo") Integer supplierNo);

    @Query(value = "select count(id) from t_reject_supplies_info where record_no in ?1 union all " +
            "select count(id) from t_reject_supplies_stock_detail where record_no in ?1 union all " +
            "select count(id) from t_pack_info where customer_id = ?2 and business_type='1' union all " +
            "select coalesce(sum(IFNULL(stock_length,0)),0) from t_pack_detail_info where order_no in ?1 union all " +
            "select count(id) from t_pack_detail_info where order_no in ?1", nativeQuery = true)
    List<String> getRejectTotalCount(@Param("orderNo") List<String> orderNo,@Param("customer") String customer);

    @Query(value = "select distinct a.record_no orderNo,b.image_name picurl,b.product_type colthModel from t_reject_record a " +
            "left join t_reject_supplies_info b on a.record_no=b.record_no " +
            "left join t_reject_supplies_stock_detail c on a.record_no=c.record_no and b.product_type=c.product_type " +
            "where a.record_no in ?1 and c.status='0'", nativeQuery = true)
    List<Map<String, Object>> getRejectWaitPieceList(@Param("recordNo") List<String> recordNo);
}
