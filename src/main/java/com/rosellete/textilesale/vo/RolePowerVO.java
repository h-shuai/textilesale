package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.RoleInfo;
import lombok.Data;

import java.util.List;

@Data
public class RolePowerVO extends RoleInfo {
    private List<String> powerList;
}
