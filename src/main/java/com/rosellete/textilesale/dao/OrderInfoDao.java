package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderInfoDao extends BaseRepository<OrderInfo, String> {

    @Query(value = "SELECT t.* FROM t_order_info t where IF(?1 is not null, t.order_no = ?1, 1 = 1) and IF(?2 is not null, t.customer_name like CONCAT(?2, '%'), 1 = 1) and IF(?3 is not null, t.customer_phone_no like CONCAT(?3, '%'), 1 = 1) and IF(?4 is not null, t.order_status = ?4, 1 = 1)", nativeQuery = true)
    List<OrderInfo> getOrderList(@Param("orderNo") String orderNo, @Param("customerName") String customerName, @Param("customerPhoneNo") String customerPhoneNo, @Param("orderStatus") String orderStatus);

    @Query(value = "SELECT t.* FROM t_order_info t where IF(?1 is not null, t.order_no=?1, 1 = 1) and IF(?2 is not null, t.customer_name like CONCAT(?2, '%'), 1 = 1) and IF(?3 is not null, t.customer_phone_no like CONCAT(?3, '%'), 1 = 1) and IF(?4 is not null, t.order_status = ?4, 1 = 1) and IF(?5 is not null, t.order_date>=?5, 1 = 1)  and IF(?6 is not null, t.order_date<?6, 1 = 1)", nativeQuery = true)
    List<OrderInfo> getOrderList(@Param("orderNo") String orderNo, @Param("customerName") String customerName, @Param("customerPhoneNo") String customerPhoneNo, @Param("orderStatus") String orderStatus, @Temporal(TemporalType.TIMESTAMP) Date startDate, @Temporal(TemporalType.TIMESTAMP) Date endDate);
}
