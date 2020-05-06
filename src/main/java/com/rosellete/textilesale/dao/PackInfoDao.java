package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.PackInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PackInfoDao extends BaseRepository<PackInfo,String> {
    @Query(value = "select coalesce(max(pack_no),0) from t_pack_info where order_no = ?1",nativeQuery = true)
    int getMaxPackNo(@Param("orderNo") String orderNo);

    @Transactional
    @Modifying
    @Query(value = "delete from t_pack_info where id = ?1",nativeQuery = true)
    int deletePackById(@Param("id") String id);

    @Query(value = "select * from t_pack_info where id = ?1",nativeQuery = true)
    PackInfo getPackInfoById(@Param("id") String id);
}
