package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.model.RoleInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends BaseRepository<RoleInfo,String>{
    @Query(value = "select * from t_role_info where IF(?1 is not null,name like CONCAT('%',?1,'%'),1=1) and IF(?2 is not null,code like CONCAT('%',?2,'%'),1=1) order by create_time desc",nativeQuery = true)
    List<RoleInfo> getRoleList(@Param("name") String name, @Param("code") String code);

    @Query(value = "SELECT SUBSTRING(code,3) inx from t_role_info ORDER BY inx DESC LIMIT 1",nativeQuery = true)
    String idex();

    @Query(value = "SELECT count(1) from t_role_info where name = ?1",nativeQuery = true)
    int isRoleName(@Param("name") String name);

    @Query(value = "SELECT r.* FROM t_role_info r WHERE r.id = ?1",nativeQuery = true)
    RoleInfo getRoleDetails(@Param("id") String id);

    @Query(value = "update t_role_info set status=1 where id = ?1",nativeQuery = true)
    int updateStatus(@Param("id") String id);
}
