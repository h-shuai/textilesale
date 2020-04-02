package com.rosellete.textilesale.service;

import com.rosellete.textilesale.model.PowerLinkRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerService {
    public List<String> getPowerList(String userId) {
        return null;
    }
    public List<String> getRolePower(String roleId) {
        return null;
    }
    public List<String> getPower() {
        return null;
    }

    public int delRolePower(String roleId){
        return 0;
    }
    public int savePower(PowerLinkRole powerLinkRole) {
        return 0;
    }
    public int updatePower(PowerLinkRole powerLinkRole) {
        return 0;
    }

    public PowerLinkRole isExit(String roleId,String powerCode) {
        return null;
    }
    public String powerId(String powerCode) {
        return null;
    }
}
