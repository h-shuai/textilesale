package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户角色关联表(TUserLinkRole)实体类
 *
 * @author makejava
 * @since 2020-04-11 17:32:01
 */
@Entity
@Table(name = "t_user_link_role")
@Data
public class UserLinkRole implements Serializable {
    private static final long serialVersionUID = 617310065197815323L;
    /**
    * id
    */
    @Id
    @Column(name = "id",unique = true,nullable = false)
    private String id;
    /**
    * 用户ID
    */
    @Column(name = "user_id")
    private String userId;
    /**
    * 角色ID
    */
    @Column(name = "role_id")
    private String roleId;
    /**
    * 有效状态
    */
    @Column(name = "status")
    private Integer status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    /**
    * 创建人
    */
    @Column(name = "create_user")
    private String createUser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;
    /**
    * 更新人
    */
    @Column(name = "update_user")
    private String updateUser;
    /**
    * 备注说明
    */
    @Column(name = "description")
    private String description;
}