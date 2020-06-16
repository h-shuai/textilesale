package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.RejectSuppliesStockDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface RejectSuppliesStockDetailDao extends BaseRepository<RejectSuppliesStockDetail,Long> {
    @Query(value = "SELECT t.id id,t.product_type productType,t.stock_length stockLength,b.image_name picurl FROM t_reject_supplies_stock_detail t,t_reject_supplies_info b where t.record_no = ?1 and t.record_no=b.record_no and t.product_type=?2 and t.status='0' and t.product_type=b.product_type",
            nativeQuery = true)
    List<Map<String,Object>> getPieceList(@Param("recordNo") Integer recordNo, @Param("productType") String productType);

    RejectSuppliesStockDetail getById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update t_reject_supplies_stock_detail set status= ?1 where id in ?2",nativeQuery = true)
    int updateStatusByIds(@Param("status") String status,@Param("id") List<String> ids);
}
