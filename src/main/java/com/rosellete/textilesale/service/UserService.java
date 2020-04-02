package com.rosellete.textilesale.service;

import com.rosellete.textilesale.model.UserInfo;
import com.rosellete.textilesale.vo.UserInfoVO;
import com.rosellete.textilesale.vo.UserLinkRoleVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    public List<UserInfoVO> getUserList(String departId, String name, String phone, Integer status) {
        List<UserInfoVO> list = null;
        return list;
    }

    public List<Map<String,Object>> getManager() {
        return null;
    }
    public int saveUser(UserInfoVO userInfoVO) {
        return 0;
    }
    public int saveUserRole(UserLinkRoleVO userLinkRole) {
        return 0;
    }


    public int updateUser(UserInfoVO userInfoVO) {
        return 0;
    }
    public int updateUserRole(UserLinkRoleVO userLinkRole) {
        return 0;
    }
    public int userRole(String userId,String roleId) {
        return 0;
    }
    public UserInfoVO getUserDetails(String userId) {
        return null;
    }

    public int getUserByAccount(String accout) {
        return 0;
    }
    public int getUserByPhone(String phone) {
        return 0;
    }
    public int getUserByCard(String card) {
        return 0;
    }

    public int updateStatus(Map param) {
        return 0;
    }

    public UserInfo getUserById(String id) {
        return null;
    }

    public List<UserInfoVO> getUsersByRoleId(String roleId) {
        List<UserInfoVO> list = null;
        return list;
    }

    public UserInfoVO getUserDetailsByAccount(String account){
        return null;
    }
}
