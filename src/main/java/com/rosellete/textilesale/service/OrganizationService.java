package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.OrganizationDao;
import com.rosellete.textilesale.dto.OrganizationDTO;
import com.rosellete.textilesale.model.Organization;
import com.rosellete.textilesale.vo.DepartmentVO;
import com.rosellete.textilesale.vo.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    public Tree<OrganizationDTO> getTreeNode(String id){
        List<OrganizationDTO> list = organizationDao.getTreePath(id);
        if (list.size() == 1) {
            OrganizationDTO organizationDTO = list.get(0);
            Tree<OrganizationDTO> tree = getDeptTree(organizationDTO);
            return tree;
        } else {
            return null;
        }
    }

    public List<Tree<OrganizationDTO>> getChildNode(String id){
        List<Tree<OrganizationDTO>> trees = new ArrayList<>();
        List<OrganizationDTO> sysDepts = organizationDao.getTreePathByPid(id);
        for (OrganizationDTO organization : sysDepts){
            Tree<OrganizationDTO> tree = getDeptTree(organization);
            trees.add(tree);
        }
        return trees;
    }

    private Tree<OrganizationDTO> getDeptTree(OrganizationDTO sysDept){
        Tree<OrganizationDTO> tree = new Tree<>();
        tree.setId(sysDept.getId());
        tree.setParentId(sysDept.getParentId());
        tree.setName(sysDept.getDepartmentName());
        Organization organization = organizationDao.getOrganizaInfoById(sysDept.getId());
        tree.setStatus(organization.getStatus());
        tree.setUserId(organization.getUserId());
        tree.setDescription(organization.getDescription());
        return tree;
    }

    public List<OrganizationDTO> getOneNode(){
        return organizationDao.getNodePath();
    }

    public List<Organization> getOrganizaInfoByParentId(String parentId){
        return organizationDao.getOrganizaInfoByParentId(parentId);
    }

    public int updateDepartment(Organization organization){
        Organization result = organizationDao.save(organization);
        return result == null ? 0 : 1;
    }

    public int insertDepartment(Organization organization){
        Organization result = organizationDao.save(organization);
        return result == null ? 0 : 1;
    }

    public int updateStatus(String id){
        return organizationDao.updateStatus(id);
    }
}
