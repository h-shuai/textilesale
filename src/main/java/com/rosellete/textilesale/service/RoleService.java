package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.RoleDao;
import com.rosellete.textilesale.model.RoleInfo;
import com.rosellete.textilesale.vo.RoleInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public List<RoleInfo> getRoleList(Map<String, Object> parameter) {
        if (parameter == null){
            return roleDao.getRoleList(null,null);
        }
        return roleDao.getRoleList((String)parameter.get("name"),(String)parameter.get("code"));
    }
    public String getIdex(){
        return roleDao.idex();
    }
    public int isRoleName(String name){
        return roleDao.isRoleName(name);
    }

    public int saveRole(RoleInfo roleInfo) {
        RoleInfo result = roleDao.save(roleInfo);
        return result == null ? 0 : 1;
    }

    public int updateRole(RoleInfo roleInfo) {
        RoleInfo result = roleDao.save(roleInfo);
        return result == null ? 0 : 1;
    }

    public RoleInfoVO getRoleDetails(String id) {
        RoleInfo roleInfo = roleDao.getRoleDetails(id);
        RoleInfoVO roleInfoVO = new RoleInfoVO();
        BeanUtils.copyProperties(roleInfo,roleInfoVO);
        return roleInfoVO;
    }

    public int updateStatus(String id) {
        return roleDao.updateStatus(id);
    }
}
