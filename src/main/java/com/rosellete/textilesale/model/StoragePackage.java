package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 入库包裹信息表实体类
 */
@Entity
@Table(name = "t_storage_package_info")
@Data
public class StoragePackage extends Page implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "package_no", nullable = false, length = 40)
    private String packageNo;

    @Column(name = "packed_stock_length", nullable = false, scale = 10,precision = 2)
    private Double packedStockLength;

    @Column(name = "record_no", nullable = false, length = 30)
    private String recordNo;

    @Column(name = "package_status", nullable = false, length = 1)
    private String packageStatus;

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
