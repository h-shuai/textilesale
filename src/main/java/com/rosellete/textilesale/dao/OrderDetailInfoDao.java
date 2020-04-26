package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.OrderDetailInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailInfoDao extends BaseRepository<OrderDetailInfo, String> {

    @Query(value = "SELECT t.* FROM t_order_detail_info t where t.order_no = ?1", nativeQuery = true)
    List<OrderDetailInfo> findAllByOrderNo(@Param("orderNo") String orderNo);

    @Query(value = "SELECT t.* FROM t_order_detail_info t where t.order_no = ?1 and t.product_type=?2", nativeQuery = true)
    List<OrderDetailInfo> findAllByOrderNoAndProductType(@Param("orderNo") String orderNo,@Param("productType") String productType);
}
