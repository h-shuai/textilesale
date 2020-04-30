package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderInfoDao extends BaseRepository<OrderInfo, String> {

    @Query(value = "SELECT t.* FROM t_order_info t where IF(?1 is not null, t.order_no= ?1, 1 = 1)" +
            " and IF(?2 is not null, t.customer_name like CONCAT(?2, '%'), 1 = 1)" +
            " and IF(?3 is not null, t.customer_phone_no like CONCAT(?3, '%'), 1 = 1)" +
            " and IF(?4 is not null, t.order_status = ?4, 1 = 1) and IF(?5 is not null, t.order_date >= ?5, 1 = 1)" +
            " and IF(?6 is not null, t.order_date < ?6, 1 = 1)", nativeQuery = true)
    List<OrderInfo> getOrderList(@Param("orderNo") String orderNo, @Param("customerName") String customerName,
                                 @Param("customerPhoneNo") String customerPhoneNo,
                                 @Param("orderStatus") String orderStatus,
                                 @Temporal(TemporalType.TIMESTAMP) Date startDate,
                                 @Temporal(TemporalType.TIMESTAMP) Date endDate);

    @Modifying
    @Query(value = "update t_order_info t set t.order_status = ?2, t.update_user = ?3, t.update_date = NOW()" +
            " where t.order_no = ?1", nativeQuery = true)
    int updateOrderStatus(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus,
                          @Param("updateUser") String updateUser);

    @Modifying
    @Query(value = "update t_order_info t set t.order_status =?2, t.order_amount = ?3, t.update_user = ?4, t.update_date = NOW()" +
            " where t.order_no = ?1", nativeQuery = true)
    int updateStatusAndAmount(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus, @Param("amount") Double amount, String updater);

    @Query(value = "select a.order_no orderNo,a.customer_name customerName,sum(b.product_length) productLength " +
            "from t_order_info a,t_order_detail_info b where a.order_status='1' and a.order_no=b.order_no " +
            "and IF(?1 is not null, a.order_no=?1, 1=1) and IF(?2 is not null, a.customer_name like CONCAT('%', ?2, '%'), 1=1) " +
            "group by a.order_no,a.customer_name order by a.order_date desc", nativeQuery = true)
    List<Map<String, Object>> getWaitPackOrderList(@Param("orderNo") String OrderNo, @Param("customerName") String customerName);

    @Query(value = "select count(id) from t_order_detail_info where order_no=?1 union all " +
            "select count(id) from t_order_stock_detail_info where order_no=?1 union all " +
            "select count(id) from t_pack_info where order_no=?1 union all " +
            "select sum(IFNULL(stock_length,0)) from t_pack_detail_info where order_no=?1 union all " +
            "select count(id) from t_pack_detail_info where order_no=?1",nativeQuery = true)
    List<String> getTotalCount(@Param("orderNo") String orderNo);

    @Query(value = "select distinct b.url picurl,b.product_type colthModel from t_order_info a " +
            "left join t_order_detail_info b on a.order_no=b.order_no " +
            "left join t_order_stock_detail_info c on a.order_no=c.order_no and b.product_type=c.product_type " +
            "where a.order_no=?1 and a.order_status='1'", nativeQuery = true)
    List<Map<String, Object>> getWaitPieceList(@Param("orderNo") String orderNo);
}
