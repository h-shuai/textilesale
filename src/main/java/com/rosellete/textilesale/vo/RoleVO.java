package com.rosellete.textilesale.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleVO {
    private List<String> roles = new ArrayList<>();

    private List<String> prems = new ArrayList<>();
}
