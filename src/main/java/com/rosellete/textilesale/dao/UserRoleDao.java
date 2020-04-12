package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.UserLinkRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends BaseRepository<UserLinkRole,String>{
    @Query(value = "select count(1) from  t_user_link_role where user_id= ?1 and role_id = ?2",nativeQuery = true)
    int userRole(@Param("userId") String userId,@Param("roleId") String roleId);

    @Query(value = "SELECT role_id FROM t_user_link_role WHERE user_id = ?1",nativeQuery = true)
    String getRole(@Param("userId") String userId);
}
