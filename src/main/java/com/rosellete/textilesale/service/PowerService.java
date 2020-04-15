package com.rosellete.textilesale.service;

import com.rosellete.textilesale.dao.PowerDao;
import com.rosellete.textilesale.dao.RolePowerDao;
import com.rosellete.textilesale.model.PowerLinkRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerService {
    @Autowired
    private PowerDao powerDao;
    @Autowired
    private RolePowerDao rolePowerDao;

    public List<String> getPowerList(String userId) {
        return powerDao.getPowerList(userId);
    }
    public List<String> getRolePower(String roleId) {
        return powerDao.getRolePower(roleId);
    }
    public List<String> getPower() {
        return powerDao.getPower();
    }

    public int delRolePower(String roleId){
        return rolePowerDao.delRolePower(roleId);
    }
    public int savePower(PowerLinkRole powerLinkRole) {
        PowerLinkRole result = rolePowerDao.save(powerLinkRole);
        return result == null ? 0 :1;
    }
    public int updatePower(PowerLinkRole powerLinkRole) {
        PowerLinkRole result = rolePowerDao.save(powerLinkRole);
        return result == null ? 0 :1;
    }

    public PowerLinkRole isExit(String roleId,String powerCode) {
        return rolePowerDao.isExit(roleId,powerCode);
    }
    public String powerId(String powerCode) {
        return powerDao.getPowerId(powerCode);
    }
}
