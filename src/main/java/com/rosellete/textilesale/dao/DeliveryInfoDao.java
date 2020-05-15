package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.DeliveryInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryInfoDao extends BaseRepository<DeliveryInfo,String> {
    @Query(value = "select coalesce(max(today_depart_num),0) from t_delivery_info where date_format(create_time,'%Y-%m-%d') = date_format(now(),'%Y-%m-%d') and type=1",nativeQuery = true)
    int getTodayDepartNum();
}
