package com.rosellete.textilesale.vo;

import lombok.Data;

import java.util.List;

@Data
public class SetPowerVO {
    private String userId;
    private String roleId;
    private List<String> powerList;
}
