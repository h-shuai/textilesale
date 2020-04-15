package com.rosellete.textilesale.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "t_role_info")
@Data
public class RoleInfo extends Page implements Serializable {
    /**
     * 角色id
     */
    @Id
    @Column(name = "id",unique = true,nullable = false)
    private String id;

    /**
     * 角色编码
     */
    @Column(name = "code")
    private String code;
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Column(name = "name",nullable = false)
    private String name;
    /**
     * 有效状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return "RoleInfo{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
