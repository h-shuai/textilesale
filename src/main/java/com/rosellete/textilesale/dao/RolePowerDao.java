package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.PowerLinkRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RolePowerDao extends BaseRepository<PowerLinkRole,String>{
    @Query(value = "select a.* from t_role_and_power a inner join t_power_info b on a.power_id = b.id where b.status =1 and IF(?1 is not null,a.role_id=?1,1=1) and IF(?2 is not null,b.power_code=?2,1=1)",nativeQuery = true)
    PowerLinkRole isExit(@Param("roleId")String roleId, @Param("powerCode")String powerCode);

    @Transactional
    @Modifying
    @Query(value = "delete from t_role_and_power where role_id = ?1",nativeQuery = true)
    int delRolePower(@Param("roleId")String roleId);
}
