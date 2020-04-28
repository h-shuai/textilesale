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
    @Column(name = "record_no",length = 30,nullable = false, unique = true)
    private String recordNo;

    @Column(name = "reject_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rejectedDate;

    @Column(name = "supplier_phone_no", length = 15)
    private String supplierPhoneNo;

    @Column(name = "supplier_type",length = 1)
    private String supplierType;

    @Column(name = "supplier_name",length = 200)
    private String supplierName;

    @Column(name = "industry_type", length = 1)
    private String industryType;

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
