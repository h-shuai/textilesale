package com.rosellete.textilesale.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_role_and_power")
@Data
public class PowerLinkRole {
    @Id
    @Column(name = "id",unique = true,nullable = false)
    private String id;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "power_id")
    private String powerId;
    @Column(name = "status")
    private int status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "create_user")
    private String createUser;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "update_user")
    private String updateUser;

    @Override
    public String toString() {
        return "PowerLinkRole{" +
                "id='" + id + '\'' +
                ", roleId='" + roleId + '\'' +
                ", powerId='" + powerId + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
