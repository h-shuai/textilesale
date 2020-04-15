package com.rosellete.textilesale.vo;

import lombok.Data;

@Data
public class LoginReqVO {
    private String username;

    private String token;

    private RoleVO roles;

    private String introdution;

    private String avatar;

    private String name;
}
