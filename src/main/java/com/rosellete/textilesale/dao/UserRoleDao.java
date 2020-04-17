package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.UserLinkRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRoleDao extends BaseRepository<UserLinkRole,String>{
    @Query(value = "select count(1) from  t_user_link_role where user_id= ?1 and role_id = ?2",nativeQuery = true)
    int userRole(@Param("userId") String userId,@Param("roleId") String roleId);

    @Query(value = "SELECT role_id FROM t_user_link_role WHERE user_id = ?1",nativeQuery = true)
    String getRole(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query(value = "update t_user_link_role set role_id = :#{#userLinkRole.roleId},update_time=:#{#userLinkRole.updateTime},update_user=:#{#userLinkRole.updateUser} where user_id=:#{#userLinkRole.userId}",nativeQuery = true)
    int updateUserRole(UserLinkRole userLinkRole);
}
