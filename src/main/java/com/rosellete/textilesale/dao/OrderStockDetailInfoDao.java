package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.OrderStockDetailInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStockDetailInfoDao extends BaseRepository<OrderStockDetailInfo, String> {
    @Query(value = "SELECT t.* FROM t_order_stock_detail_info t where t.order_no = ?1 and t.product_type=?2", nativeQuery = true)
    List<OrderStockDetailInfo> findAllByOrderNoAndProductType(@Param("orderNo") String orderNo, @Param("productType") String productType);
}
