package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 入库信息表实体类
 */
@Entity
@Table(name = "t_storage_record")
@Data
public class StorageRecord extends Page implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "record_no", length = 6, nullable = false, unique = true)
    private Integer recordNo;

    @Column(name = "storage_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date storageDate;

    @Column(name = "storage_type", nullable = false, length = 1)
    private String storageType;

    @Column(name = "consignor_no",nullable = false, length = 11)
    private Integer consignorNo;

    @Column(name = "package_quantity", length = 8)
    private Integer packageQuantity;

    @Column(name = "storage_clerk_name", length = 200)
    private String storageClerkName;

    @Column(name = "create_user", length = 100)
    private String createUser;

    @Column(name = "update_user", length = 100)
    private String updateUser;

    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
}
