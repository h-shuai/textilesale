package com.rosellete.textilesale.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 退回供应商记录实体类
 */
@Entity
@Table(name = "t_reject_record")
@Data
public class RejectRecord extends Page implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "record_no", length = 30, nullable = false, unique = true)
    private String recordNo;

    @Column(name = "reject_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rejectedDate;

    @Column(name = "supplier_no",nullable = false, length = 11)
    private Integer supplierNo;

    @Column(name = "product_type_count", nullable = false, length = 11)
    private Integer productTypeCount;

    @Column(name = "product_length_sum",nullable = false, scale = 16, precision = 2)
    private Double rejectSumLength;

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
