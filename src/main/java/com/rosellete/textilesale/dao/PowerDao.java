package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.PowerInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerDao extends BaseRepository<PowerInfo,String>{
    @Query(value = "SELECT c.power_code FROM t_user_link_role a INNER JOIN t_role_and_power b ON a.role_id = b.role_id INNER JOIN t_power_info c ON b.power_id = c.id where a.user_id= ?1 and b.status =1 and c.status = 1",nativeQuery = true)
    List<String> getPowerList(String userId);
}
