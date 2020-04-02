package com.rosellete.textilesale.dao;

import com.rosellete.textilesale.dto.OrganizationDTO;
import com.rosellete.textilesale.model.Organization;
import com.rosellete.textilesale.vo.DepartmentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationDao extends BaseRepository<Organization,String> {
    @Query("select d.id,d.parent_id,d.department_name FROM t_department_info d where d.id = ?1")
    List<OrganizationDTO> getTreePath(@Param("id")String id);

    @Query("SELECT d.id,d.parent_id,d.department_name FROM t_department_info d where d.parent_id is null")
    List<OrganizationDTO> getNodePath();

    @Query("SELECT d.id,d.parent_id,d.department_name FROM t_department_info d where d.parent_id = ?1")
    List<OrganizationDTO> getTreePathByPid(@Param(value="pid") String pid);

    @Query("SELECT d.id,d.status,d.parent_id,d.description,d.user_id,d.department_name as name FROM t_department_info d where d.id = ?1")
    DepartmentVO getOrganizaInfoById(@Param(value="id") String id);

    @Query("SELECT d FROM t_department_info d WHERE d.parent_id = ?1")
    List<Organization> getOrganizaInfoByParentId(@Param(value="parentId") String parentId);

    int updateDepartment(Organization organization);

    int insertDepartment(Organization organization);

    @Query("SELECT d.id,d.status,d.parent_id,d.description,d.user_id FROM t_department_info d")
    List<DepartmentVO> getAllOrgList();
}
