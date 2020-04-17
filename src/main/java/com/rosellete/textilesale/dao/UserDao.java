package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao extends BaseRepository<UserInfo,String>{
    @Query(value = "select u.* ,r.id roleId,r.code roleCode,r.name roleName from t_user_info u INNER JOIN t_user_link_role l on u.id = l.user_id INNER JOIN t_role_info r on l.role_id = r.id where IF(?2 is not null,u.name like CONCAT('%',?2,'%'),1=1) AND IF(?3 is not null,u.phone like CONCAT('%',?3,'%'),1=1) AND IF(?4 is not null,u.status= ?4,1=1) AND IF(?1 is not null,u.depart_id= ?1,1=1) order by u.create_time desc",nativeQuery = true)
    List<Map<String,Object>> getUserList(@Param("departId") String departId,
                                 @Param("name") String name,
                                 @Param("phone") String phone,
                                 @Param("status") Integer status);

    @Query(value = "select id,name,phone from t_user_info",nativeQuery = true)
    List<Map<String,Object>> getManager();

    @Query(value = "SELECT u.* FROM t_user_info u WHERE u.id = ?1",nativeQuery = true)
    UserInfo getUserDetails(@Param("userId") String userId);

    @Query(value = "SELECT count(1) FROM t_user_info u WHERE u.account = ?1",nativeQuery = true)
    int getUserByAccount(@Param("account") String account);

    @Query(value = "SELECT count(1) FROM t_user_info u WHERE u.phone = ?1",nativeQuery = true)
    int getUserByPhone(@Param("phone") String phone);

    @Query(value = "SELECT count(1) FROM t_user_info u WHERE u.card = ?1",nativeQuery = true)
    int getUserByCard(@Param("card") String card);

    @Query(value = "SELECT u.* FROM t_user_info u JOIN t_user_link_role ur ON u.id = ur.user_id WHERE ur.role_id = ?1",nativeQuery = true)
    List<UserInfo> getUsersByRoleId(@Param("roleId") String roleId);

    @Query(value = "SELECT u.* FROM t_user_info u where u.account = ?1",nativeQuery = true)
    UserInfo getUserDetailsByAccount(@Param("account") String account);

    @Query(value = "SELECT u.* FROM t_user_info u where (u.account = ?1 or u.phone = ?1) and u.password = ?2",nativeQuery = true)
    UserInfo getLoginUserByAccount(@Param("account") String account,@Param("password") String password);

    @Query(value = "SELECT u.* FROM t_user_info u where u.id = ?1",nativeQuery = true)
    UserInfo getUserById(@Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "update t_user_info set status=?1,update_time=now(),update_user='' where id=?2",nativeQuery = true)
    int updateStatus(@Param("status") Integer status,@Param("id") String id);
}
