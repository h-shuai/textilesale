package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_department_info")
@Data
public class Organization implements Serializable {
    /**
     * 机构id
     */
    @Id
    @Column(name = "id",unique = true,nullable = false)
    private String id;
    /**
     * 部门负责人id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 部门名称
     */
    @Column(name = "department_name")
    private String  departmentName;
    /**
     * 父类机构id
     */
    @Column(name = "parent_id")
    private String parentId;
    /**
     * 部门状态
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 节点深度
     */
    @Column(name = "path_length")
    private int pathLength;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //入参
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;
    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;
    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 创建人
     */
    @Column(name = "create_user")
    private String createUser;
    /**
     * 更新人id
     */
    private String updateUserId;
    /**
     * 更新人
     */
    @Column(name = "update_user")
    private String updateUser;

    @Override
    public String toString() {
        return "Organization{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", status=" + status +
                ", pathLength=" + pathLength +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createUserId='" + createUserId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUserId='" + updateUserId + '\'' +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
