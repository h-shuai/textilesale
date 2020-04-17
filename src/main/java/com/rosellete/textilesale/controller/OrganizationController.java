package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.business.OrganizationBusiness;
import com.rosellete.textilesale.dto.OrganizationDTO;
import com.rosellete.textilesale.interfaces.OrganizationApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DepartmentVO;
import com.rosellete.textilesale.vo.Tree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
@Slf4j
public class OrganizationController implements OrganizationApi {
    @Autowired
    private OrganizationBusiness organizationBusiness;

    @Override
    public RestResponse getTreePath() {
        List<Tree<OrganizationDTO>> treeList = organizationBusiness.getTreePath();
        return new RestResponse(treeList);
    }

    @Override
    public RestResponse updateDepartment(@RequestBody DepartmentVO departmentVO) {
        return organizationBusiness.updateDepartment(departmentVO);
    }

    @Override
    public RestResponse insertDepartment(@RequestBody DepartmentVO departmentVO) {
        return organizationBusiness.insertDepartment(departmentVO);
    }
}
