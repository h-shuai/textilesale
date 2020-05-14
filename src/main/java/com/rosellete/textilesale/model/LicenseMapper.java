package com.rosellete.textilesale.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 车牌数据字典(TLicenseMapper)实体类
 *
 * @author makejava
 * @since 2020-05-12 14:06:12
 */
@Entity
@Table(name = "t_license_mapper")
@Data
public class LicenseMapper implements Serializable {
    private static final long serialVersionUID = 400619178350222564L;
    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;
    /**
    * 车牌号
    */
    @Column(name = "license_no")
    private String licenseNo;
    /**
    * 状态：1-有效，0-无效
    */
    @Column(name = "status")
    private Integer status;
    /**
    * 是否限行：0-否，1-是
    */
    @Column(name = "if_restriction")
    private Integer ifRestriction;
}
