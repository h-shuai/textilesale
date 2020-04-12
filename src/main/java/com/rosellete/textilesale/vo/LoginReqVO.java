package com.rosellete.textilesale.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginReqVO {
    private String username;

    private String token;

    private List<String> role = new ArrayList<>();

    private String introdution;

    private String avatar;

    private String name;
}
