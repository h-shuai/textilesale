package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.AccountMain;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMainDao extends BaseRepository<AccountMain,Long> {
    @Query(value = "select * from t_account_main where validstatus = '1' and IF(?1 is not null,customer_name like CONCAT('%', ?1, '%'),1=1) order by create_date desc",nativeQuery = true)
    Page<AccountMain> getPageByCustomer(@Param("customerName") String customerName, Pageable pageable);

    AccountMain getAccountMainByCustomerNo(@Param("customerNo") String customerNo);
}
