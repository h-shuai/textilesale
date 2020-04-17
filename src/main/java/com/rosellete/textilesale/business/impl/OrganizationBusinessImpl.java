package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.OrganizationBusiness;
import com.rosellete.textilesale.dto.OrganizationDTO;
import com.rosellete.textilesale.model.Organization;
import com.rosellete.textilesale.service.OrganizationService;
import com.rosellete.textilesale.util.NullPropertiesUtil;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DepartmentVO;
import com.rosellete.textilesale.vo.Tree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrganizationBusinessImpl implements OrganizationBusiness {

    @Autowired
    private OrganizationService organizationService;

    @Override
    public List<Tree<OrganizationDTO>> getTreePath() {
        List<Tree<OrganizationDTO>> tree = new ArrayList<>();
        List<OrganizationDTO> oneNode = organizationService.getOneNode();
        for (OrganizationDTO node1: oneNode) {
            tree.add(getTreePath(node1.getId()));
        }
        return tree;
    }

    private Tree<OrganizationDTO> getTreePath(String id) {
        Tree<OrganizationDTO> deptNode = organizationService.getTreeNode(id);
        if (deptNode == null){
            return null;
        }
        List<Tree<OrganizationDTO>> childNode = organizationService.getChildNode(id);
        if(null==childNode){
            return null;
        }
        for (Tree<OrganizationDTO> child:childNode) {
            Tree<OrganizationDTO> node = getTreePath(child.getId());
            deptNode.getChildren().add(node);
        }

        if(0==deptNode.getChildren().size()){
            deptNode.setChildren(null);
        }
        return deptNode;
    }

    @Override
    public RestResponse updateDepartment(DepartmentVO departmentVO) {
        if(StringUtils.isEmpty(departmentVO.getId())
                || StringUtils.isEmpty(departmentVO.getName())
                || StringUtils.isEmpty(departmentVO.getStatus().toString())){
            return new RestResponse(401,"参数错误");
        }
        try{
            Organization organiza = organizationService.getInfoById(departmentVO.getId());
            BeanUtils.copyProperties(departmentVO,organiza, NullPropertiesUtil.getNullPropertyNames(departmentVO));
            organiza.setDepartmentName(departmentVO.getName());
            organiza.setUpdateDate(new Date());
            organizationService.updateDepartment(organiza);
            return new RestResponse();
        }catch (Exception e){
            return new RestResponse(500,"修改失败");
        }
    }

    @Override
    public RestResponse insertDepartment(DepartmentVO departmentVO) {
        if(StringUtils.isEmpty(departmentVO.getName())
                || StringUtils.isEmpty(departmentVO.getStatus().toString())){
            return new RestResponse(401,"参数错误");
        }
        Organization organiza = new Organization();
        List<Organization> lowerLevel = organizationService.getOrganizaInfoByParentId(departmentVO.getId());
        String pathId = null;
        if (lowerLevel.size()!=0){
            List<Integer> nums = new ArrayList<Integer>();
            for (Organization lower :lowerLevel) {
                String strhours =lower.getId();
                Integer path = Integer.parseInt(strhours);
                nums.add(path);
            }
            pathId = "0"+String.valueOf(Collections.max(nums)+1);
        }else{
            pathId = departmentVO.getId()+"01";
        }
        BeanUtils.copyProperties(departmentVO,organiza);
        organiza.setId(pathId);
        organiza.setParentId(departmentVO.getId());
        organiza.setDepartmentName(departmentVO.getName());
        organiza.setCreateDate(new Date());
        organiza.setCreateUser("");
        organizationService.insertDepartment(organiza);
        return new RestResponse();
    }
}
