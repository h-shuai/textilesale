package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.AccountDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AccountDetailDao extends BaseRepository<AccountDetail,Long> {
    @Query(value = "select * from t_account_detail where main_id = ?1 and pay_date between ?2 and ?3 order by pay_date desc",nativeQuery = true)
    Page<AccountDetail> getPageByParam(@Param("mainId") Long mainId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}
