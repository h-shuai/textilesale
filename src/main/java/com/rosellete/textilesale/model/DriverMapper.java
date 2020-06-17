package com.rosellete.textilesale.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 司机数据字段(TDriverMapper)实体类
 *
 * @author makejava
 * @since 2020-05-12 14:06:10
 */
@Entity
@Table(name = "t_driver_mapper")
@Data
public class DriverMapper implements Serializable {
    private static final long serialVersionUID = 777863162737483712L;
    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;
    /**
    * 司机名称
    */
    @Column(name = "driver_name")
    private String driverName;
    /**
    * 司机联系电话
    */
    @Column(name = "driver_phone")
    private String driverPhone;
    /**
    * 状态：1-有效，0-无效
    */
    @Column(name = "status")
    private Integer status;
    /**
     * 是否内部：1-是，2-否
     */
    @Column(name = "is_self")
    private Integer isSelf;
}
