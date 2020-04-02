package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.UserInfo;
import lombok.Data;

@Data
public class UserInfoVO extends UserInfo {
    private String roleId;//角色id

    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
}
