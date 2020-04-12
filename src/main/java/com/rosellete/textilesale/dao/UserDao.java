package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.UserInfo;
import com.rosellete.textilesale.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao extends BaseRepository<UserInfo,String>{
    @Query(value = "select u.* ,r.id roleId,r.code roleCode,r.name roleName from t_user_info u INNER JOIN t_user_link_role l on u.id = l.user_id INNER JOIN t_role_info r on l.role_id = r.id where u.name like CONCAT('%',?1,'%') AND u.phone like CONCAT('%',?2,'%') AND u.status= ?3 AND u.depart_id= ?4 order by u.create_time desc",nativeQuery = true)
    List<UserInfoVO> getUserList(@Param("departId") String departId,
                                 @Param("name") String name,
                                 @Param("phone") String phone,
                                 @Param("status") Integer status);

    @Query(value = "select id,name,phone from t_user_info",nativeQuery = true)
    List<Map<String,Object>> getManager();

    @Query(value = "SELECT u.* FROM t_user_info u WHERE u.id = ?1",nativeQuery = true)
    UserInfoVO getUserDetails(@Param("userId") String userId);

    @Query(value = "SELECT count(1) FROM t_user_info u WHERE u.account = ?1",nativeQuery = true)
    int getUserByAccount(@Param("account") String account);

    @Query(value = "SELECT count(1) FROM t_user_info u WHERE u.phone = ?1",nativeQuery = true)
    int getUserByPhone(@Param("phone") String phone);

    @Query(value = "SELECT count(1) FROM t_user_info u WHERE u.card = ?1",nativeQuery = true)
    int getUserByCard(@Param("card") String card);

    @Query(value = "SELECT u.* FROM t_user_info u JOIN t_user_link_role ur ON u.id = ur.user_id WHERE ur.role_id = ?1",nativeQuery = true)
    List<UserInfoVO> getUsersByRoleId(@Param("roleId") String roleId);

    @Query(value = "SELECT u.* FROM t_user_info u where u.account = ?1",nativeQuery = true)
    UserInfoVO getUserDetailsByAccount(@Param("account") String account);

    @Query(value = "SELECT u.* FROM t_user_info u where (u.account = ?1 or u.phone = ?1) and u.password = ?2",nativeQuery = true)
    UserInfo getLoginUserByAccount(@Param("account") String account,@Param("password") String password);

    @Query(value = "SELECT u.* FROM t_user_info u where u.id = ?1",nativeQuery = true)
    UserInfo getUserById(@Param("id") String id);
}
