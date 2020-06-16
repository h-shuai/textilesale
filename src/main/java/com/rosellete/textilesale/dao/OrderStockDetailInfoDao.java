package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.OrderStockDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderStockDetailInfoDao extends BaseRepository<OrderStockDetail, Long> {
    @Query(value = "SELECT t.* FROM t_order_stock_detail_info t where t.order_no = ?1 and t.product_type=?2",
            nativeQuery = true)
    List<OrderStockDetail> findAllByOrderNoAndProductType(@Param("orderNo") Integer orderNo,
                                                          @Param("productType") String productType);

    @Query(value = "SELECT t.id id,t.product_type productType,t.stock_length stockLength,b.image_name picurl FROM t_order_stock_detail_info t,t_order_detail_info b where t.order_no = ?1 and t.order_no=b.order_no and t.product_type=?2 and t.status='0' and t.product_type=b.product_type",
            nativeQuery = true)
    List<Map<String,Object>> getPieceList(@Param("orderNo") Integer orderNo, @Param("productType") String productType);

    @Transactional
    @Modifying
    @Query(value = "update t_order_stock_detail_info set status= ?1 where id in ?2",nativeQuery = true)
    int updateStatusByIds(@Param("status") String status,@Param("id") List<String> ids);

    @Query(value = "select * from t_order_stock_detail_info where id = ?1",nativeQuery = true)
    OrderStockDetail getStockDetailById(@Param("id") String id);
}
