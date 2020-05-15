package com.rosellete.textilesale.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 指定托运部数据字典(TCosignMapper)实体类
 *
 * @author makejava
 * @since 2020-05-13 11:33:51
 */
@Entity
@Table(name = "t_cosign_mapper")
@Data
public class CosignMapper implements Serializable {
    private static final long serialVersionUID = -60303598965481789L;
    /**
    * 主键ID
    */
    @Id
    @Column(name = "id", unique = true,nullable = false)
    private String id;
    /**
    * 指定托运部
    */
    @Column(name = "cosign_name")
    private String cosignName;
    /**
    * 托运部地址
    */
    @Column(name = "cosign_address")
    private String cosignAddress;
    /**
    * 托运部联系电话
    */
    @Column(name = "cosign_phone")
    private String cosignPhone;
    /**
    * 状态：1-有效，0-无效
    */
    @Column(name = "status")
    private Integer status;
    /**
    * 托运部联系人
    */
    @Column(name = "cosign_link_user")
    private String cosignLinkUser;
}
