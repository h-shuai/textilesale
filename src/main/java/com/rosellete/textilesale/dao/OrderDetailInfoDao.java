package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.OrderDetailInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderDetailInfoDao extends BaseRepository<OrderDetailInfo, Long> {

    @Query(value = "SELECT t.* FROM t_order_detail_info t where t.order_no = ?1", nativeQuery = true)
    List<OrderDetailInfo> findAllByOrderNo(@Param("orderNo") String orderNo);

    @Query(value = "SELECT t.* FROM t_order_detail_info t where t.order_no = ?1 and t.product_type = ?2",
            nativeQuery = true)
    List<OrderDetailInfo> findAllByOrderNoAndProductType(@Param("orderNo") String orderNo,
                                                         @Param("productType") String productType);

    @Query(value = "SELECT t2.*, t1.customer_name, t1.customer_phone_no, t1.order_date, t1.delivery_mode," +
            " t1.consignment_department FROM t_order_info t1,t_order_detail_info t2" +
            " where t1.order_no = t2.order_no and IF(?1 is not null, t2.order_no = ?1, 1 = 1)" +
            " and IF(?2 is not null, t2.product_type = ?2, 1 = 1)" +
            " and IF(?3 is not null, t1.customer_name like CONCAT(?3, '%'), 1 = 1)" +
            " and IF(?4 is not null, t1.delivery_mode = ?4, 1 = 1)" +
            " and IF(?5 is not null, t1.consignment_department = ?5, 1 = 1)", nativeQuery = true)
    List<Map<String, Object>> findByCustomerInfoAndProductType(@Param("orderNo") String orderNo,
                                                               @Param("productType") String productType,
                                                               @Param("customerName") String customerName,
                                                               @Param("deliveryMode") String deliveryMode,
                                                               @Param("consignmentDepartment") String consignmentDepartment);

    @Query(value = "SELECT t2.*, t1.customer_name, t1.customer_phone_no, t1.order_date, t1.delivery_mode," +
            " t1.consignment_department FROM t_order_info t1,t_order_detail_info t2" +
            " where t1.order_no = t2.order_no and IF(?1 is not null, t2.order_no = ?1, 1 = 1)" +
            " and IF(?2 is not null, t2.product_type = ?2, 1 = 1)" +
            " and IF(?3 is not null, t1.customer_name like CONCAT(?3, '%'), 1 = 1)" +
            " and IF(?4 is not null, t1.delivery_mode = ?4, 1 = 1)" +
            " and IF(?5 is not null, t1.consignment_department = ?5, 1 = 1)" +
            " and IF(?6 is not null, t1.order_date >= ?6, 1 = 1)" +
            "  and IF(?7 is not null, t1.order_date < ?7, 1 = 1)order by t1.order_date desc,t2.order_no asc limit ?8,?9 ", nativeQuery = true)
    List<Map<String, Object>> findPageByCustomerInfoAndDate(@Param("orderNo") String orderNo,
                                                            @Param("productType") String productType,
                                                            @Param("customerName") String customerName,
                                                            @Param("deliveryMode") String deliveryMode,
                                                            @Param("consignmentDepartment") String consignmentDepartment,
                                                            @Temporal(TemporalType.TIMESTAMP) Date startDate,
                                                            @Temporal(TemporalType.TIMESTAMP) Date endDate,
                                                            @Param("startRow") Integer startRow, @Param("size") Integer size);

    @Query(value = "SELECT t2.*, t1.customer_name, t1.customer_phone_no, t1.order_date, t1.delivery_mode," +
            " t1.consignment_department FROM t_order_info t1,t_order_detail_info t2" +
            " where t1.order_no = t2.order_no and IF(?1 is not null, t2.order_no = ?1, 1 = 1)" +
            " and IF(?2 is not null, t2.product_type = ?2, 1 = 1)" +
            " and IF(?3 is not null, t1.customer_name like CONCAT(?3, '%'), 1 = 1)" +
            " and IF(?4 is not null, t1.delivery_mode = ?4, 1 = 1)" +
            " and IF(?5 is not null, t1.consignment_department = ?5, 1 = 1)" +
            " and IF(?6 is not null, t1.order_date >= ?6, 1 = 1)" +
            "  and IF(?7 is not null, t1.order_date < ?7, 1 = 1) order by t1.order_date desc,t2.order_no asc", nativeQuery = true)
    List<Map<String, Object>> findByCustomerInfoAndDate(@Param("orderNo") String orderNo,
                                                        @Param("productType") String productType,
                                                        @Param("customerName") String customerName,
                                                        @Param("deliveryMode") String deliveryMode,
                                                        @Param("consignmentDepartment") String consignmentDepartment,
                                                        @Temporal(TemporalType.TIMESTAMP) Date startDate,
                                                        @Temporal(TemporalType.TIMESTAMP) Date endDate);
}
