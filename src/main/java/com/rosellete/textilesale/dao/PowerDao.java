package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.PowerInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerDao extends BaseRepository<PowerInfo,String>{
    @Query(value = "SELECT c.power_code FROM t_user_link_role a INNER JOIN t_role_and_power b ON a.role_id = b.role_id INNER JOIN t_power_info c ON b.power_id = c.id where a.user_id= ?1 and b.status =1 and c.status = 1",nativeQuery = true)
    List<String> getPowerList(String userId);

    @Query(value = "SELECT DISTINCT power_code FROM t_power_info a INNER JOIN t_role_and_power b ON a.id = b.power_id where a.status = 1",nativeQuery = true)
    List<String> getPower();

    @Query(value = "SELECT DISTINCT a.power_code FROM t_power_info a INNER JOIN t_role_and_power b ON a.id = b.power_id where b.status =1 and a.status =1 and IF(?1 is not null,b.role_id=?1,1=1)",nativeQuery = true)
    List<String> getRolePower(@Param("roleId")String roleId);

    @Query(value = "select id from t_power_info where status = 1 and IF(?1 is not null,power_code=?1,1=1)",nativeQuery = true)
    String getPowerId(@Param("powerCode")String powerCode);
}
