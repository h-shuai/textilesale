package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 入库物品信息实体类
 */
@Entity
@Table(name = "t_package_inventory_info")
@Data
@NoArgsConstructor
public class PackageInventory extends Page implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "package_no", nullable = false, length = 40)
    private String packageNo;

    @Column(name = "stock_no", nullable = false)
    private Integer stockNo;

    @Column(name = "product_type", nullable = false, length = 10)
    private String productType;

    @Column(name = "stock_length", nullable = false, scale = 8, precision = 2)
    private Double stockLength;

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

    @Column(name = "image_name")
    private String imageName;
}
