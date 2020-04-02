package com.rosellete.textilesale.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrganizationDTO implements Serializable {
    private String parentId;
    private String id;
    private String departmentName;
    private List<OrganizationDTO> children;
}
