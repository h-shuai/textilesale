package com.rosellete.textilesale.service;

import com.alibaba.fastjson.JSONObject;
import com.rosellete.textilesale.dao.OrganizationDao;
import com.rosellete.textilesale.dto.OrganizationDTO;
import com.rosellete.textilesale.model.Organization;
import com.rosellete.textilesale.vo.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    public Tree<OrganizationDTO> getTreeNode(String id){
        List<Map<String,Object>> treeMaps = organizationDao.getTreePath(id);
        List<OrganizationDTO> list = new ArrayList<>();
        for (Map<String,Object> treeMap : treeMaps){
            OrganizationDTO organizationDTO = JSONObject.parseObject(JSONObject.toJSONString(treeMap),OrganizationDTO.class);
            list.add(organizationDTO);
        }
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
        List<Map<String,Object>> maps = organizationDao.getTreePathByPid(id);
        for (Map<String,Object> map : maps){
            OrganizationDTO organizationDTO = JSONObject.parseObject(JSONObject.toJSONString(map),OrganizationDTO.class);
            Tree<OrganizationDTO> tree = getDeptTree(organizationDTO);
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
        List<Map<String,Object>> maps = organizationDao.getNodePath();
        List<OrganizationDTO> list = new ArrayList<>();
        for (Map<String,Object> map : maps){
            OrganizationDTO organizationDTO = JSONObject.parseObject(JSONObject.toJSONString(map),OrganizationDTO.class);
            list.add(organizationDTO);
        }
        return list;
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

    public Organization getInfoById(String id){
        return organizationDao.getOrganizaInfoById(id);
    }
}
