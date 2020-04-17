package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.dto.OrganizationDTO;
import com.rosellete.textilesale.model.Organization;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrganizationDao extends BaseRepository<Organization,String> {
    @Query(value = "select d.id as id,d.parent_id as parentId,d.department_name as departmentName FROM t_department_info d where d.id = ?1",nativeQuery = true)
    List<Map<String,Object>> getTreePath(@Param("id")String id);

    @Query(value = "SELECT d.id as id,d.parent_id as parentId,d.department_name as departmentName FROM t_department_info d where d.parent_id is null",nativeQuery = true)
    List<Map<String,Object>> getNodePath();

    @Query(value = "SELECT d.id as id,d.parent_id departId,d.department_name as departmentName FROM t_department_info d where d.parent_id = ?1",nativeQuery = true)
    List<Map<String,Object>> getTreePathByPid(@Param(value="pid") String pid);

    @Query(value = "SELECT d.* FROM t_department_info d where d.id = ?1",nativeQuery = true)
    Organization getOrganizaInfoById(@Param(value="id") String id);

    @Query(value = "SELECT d.* FROM t_department_info d WHERE d.parent_id = ?1",nativeQuery = true)
    List<Organization> getOrganizaInfoByParentId(@Param(value="parentId") String parentId);

    @Query(value = "SELECT d.* FROM t_department_info d",nativeQuery = true)
    List<Organization> getAllOrgList();
}
