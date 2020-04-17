package com.rosellete.textilesale.business;

import com.rosellete.textilesale.dto.OrganizationDTO;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DepartmentVO;
import com.rosellete.textilesale.vo.Tree;

import java.util.List;

public interface OrganizationBusiness {
    List<Tree<OrganizationDTO>> getTreePath();

    RestResponse updateDepartment(DepartmentVO departmentVO);

    RestResponse insertDepartment(DepartmentVO departmentVO);
}
