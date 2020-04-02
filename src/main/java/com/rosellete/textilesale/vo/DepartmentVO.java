package com.rosellete.textilesale.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DepartmentVO {
    private String id;

    private String parentId;

    @NotBlank(message = "部门名称不能为空")
    private String name;//显示节点文本
    /**
     * 部门负责人id
     */
    private String userId;

    /**
     * 描述
     */
    private String description;
    /**
     * 部门状态
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
