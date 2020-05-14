package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.PackInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface PackInfoDao extends BaseRepository<PackInfo,String> {
    @Query(value = "select coalesce(max(pack_no),0) from t_pack_info where customer_id = ?1",nativeQuery = true)
    int getMaxPackNo(@Param("customer") String customer);

    @Transactional
    @Modifying
    @Query(value = "delete from t_pack_info where id = ?1",nativeQuery = true)
    int deletePackById(@Param("id") String id);

    @Query(value = "select * from t_pack_info where id = ?1",nativeQuery = true)
    PackInfo getPackInfoById(@Param("id") String id);

    @Query(value = "select * from t_pack_info where customer_name = ?1 and IF(?2 is not null,status = ?2,1=1)",nativeQuery = true)
    List<PackInfo> getPackListByCustomer(@Param("customer") String customer,@Param("status") String status);

    @Query(value = "select customer_name customerName,count(1) packNum,'托运部1' consignDep from t_pack_info where IF(?1 is not null,customer_name = ?1,1=1) and status=1 group by customer_name",nativeQuery = true)
    List<Map<String,Object>> getWaitDeliveryList(@Param("customer") String customer);

    @Transactional
    @Modifying
    @Query(value = "update t_pack_info set status=2 where id in ?1",nativeQuery = true)
    void updateStatusByIds(@Param("ids") List<String> ids);
}
