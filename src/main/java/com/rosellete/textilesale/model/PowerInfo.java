package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 权限表(TPowerInfo)实体类
 *
 * @author makejava
 * @since 2020-04-12 15:09:10
 */
@Entity
@Table(name = "t_power_info")
@Data
public class PowerInfo implements Serializable {
    private static final long serialVersionUID = 992139561883942376L;
    /**
    * 主键ID
    */
    @Id
    @Column(name = "id",unique = true,nullable = false)
    private String id;
    /**
    * 权限标识
    */
    @Column(name = "power_code")
    private String powerCode;
    /**
    * 权限说明
    */
    @Column(name = "simple")
    private String simple;
    /**
    * 有效状态
    */
    @Column(name = "status")
    private String status;

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