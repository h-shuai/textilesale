package com.rosellete.textilesale.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 退回供应商详情表实体类
 */
@Entity
@Table(name = "t_reject_supplies_stock_detail")
@Data
public class RejectSuppliesStockDetail extends Page implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 30)
    private Long id;

    @Column(name = "record_no", nullable = false, length = 30)
    private String recordNo;

    @Column(name = "product_type", nullable = false, length = 10)
    private String productType;

    @Column(name = "stock_no", nullable = false)
    private Integer stockNo;

    @Column(name = "stock_length", nullable = false, scale = 8, precision = 2)
    private Double stockLength;

    @Column(name = "status", nullable = true, length = 1)
    private String status;

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
