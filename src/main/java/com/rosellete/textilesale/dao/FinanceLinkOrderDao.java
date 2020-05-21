package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.FinanceLinkOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FinanceLinkOrderDao extends BaseRepository<FinanceLinkOrder,String> {
    @Query(value = "select batch_no batchNo,sum(order_amount) totalAmount,create_date createDate from t_finance_link_order group by batch_no,create_date order by create_date",nativeQuery = true)
    List<Map<String,Object>> getFinanceBatchList();

    @Query(value ="select order_no orderNo,order_amount orderAmount from t_finance_link_order where batch_no = ?1",nativeQuery = true)
    List<Map<String,Object>> getByBatchNo(@Param("batchNo") String batchNo);
}
