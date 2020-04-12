package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.dto.OrganizationDTO;
import com.rosellete.textilesale.model.Organization;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationDao extends BaseRepository<Organization,String> {
    @Query(value = "select d.id,d.parent_id,d.department_name FROM t_department_info d where d.id = ?1",nativeQuery = true)
    List<OrganizationDTO> getTreePath(@Param("id")String id);

    @Query(value = "SELECT d.id,d.parent_id,d.department_name FROM t_department_info d where d.parent_id is null",nativeQuery = true)
    List<OrganizationDTO> getNodePath();

    @Query(value = "SELECT d.id,d.parent_id,d.department_name FROM t_department_info d where d.parent_id = ?1",nativeQuery = true)
    List<OrganizationDTO> getTreePathByPid(@Param(value="pid") String pid);

    @Query(value = "SELECT d.* FROM t_department_info d where d.id = ?1",nativeQuery = true)
    Organization getOrganizaInfoById(@Param(value="id") String id);

    @Query(value = "SELECT d FROM t_department_info d WHERE d.parent_id = ?1",nativeQuery = true)
    List<Organization> getOrganizaInfoByParentId(@Param(value="parentId") String parentId);

    @Query(value = "SELECT d.id,d.status,d.parent_id,d.description,d.user_id FROM t_department_info d",nativeQuery = true)
    List<Organization> getAllOrgList();

    @Query(value = "update t_department_info set status=1 where id = ?1",nativeQuery = true)
    int updateStatus(String id);
}
