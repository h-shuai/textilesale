package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.FinanceDetailInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FinanceDetailInfoDao extends BaseRepository<FinanceDetailInfo,String> {
    @Query(value = "select b.method_name payMethod,a.pay_amount payAmount from t_finance_detail_info a,t_paymethod_mapper b where a.paymethod_id = b.id and a.batch_no= ?1",nativeQuery = true)
    List<Map<String,Object>> getDetailListByBatchNo(@Param("batchNo") String batchNo);
}
