package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.PackDetailInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface PackDetailInfoDao extends BaseRepository<PackDetailInfo,String> {
    @Transactional
    @Modifying
    @Query(value = "delete from t_pack_detail_info where pack_id = ?1",nativeQuery = true)
    int deleteDetailByPackId(@Param("id") String id);

    @Query(value = "select distinct order_no orderNo,product_type productType,prod_pic prodPic from t_pack_detail_info where pack_id = ?1",nativeQuery = true)
    List<Map<String,Object>> getProductMap(@Param("packId") String packId);

    @Query(value = "select id,stock_length stockLength from t_pack_detail_info where pack_id = ?1 and product_type = ?2 and order_no = ?3",nativeQuery = true)
    List<Map<String,Object>> getPackDetailRices(@Param("packId") String packId, @Param("product_type") String product_type,@Param("orderNo") String orderNo);

    @Query(value = "select * from t_pack_detail_info where pack_id = ?1",nativeQuery = true)
    List<PackDetailInfo> getPackDetailsByPackId(@Param("packId") String packId);
}
