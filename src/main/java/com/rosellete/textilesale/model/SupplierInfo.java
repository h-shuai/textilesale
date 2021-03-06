package com.rosellete.textilesale.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "t_supplier_info")
@Data
public class SupplierInfo extends Page implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "supplier_no", nullable = false, length = 30)
    private Integer supplierNo;

    @Column(name = "name", length = 200)
    private String supplierName;

    @Column(name = "short_name", length = 200)
    private String shortName;

    @Column(name = "phone", length = 200)
    private String supplierPhoneNo;

    @Column(name = "type", length = 200)
    private String supplierType;

    @Column(name = "address", length = 200)
    private String supplierAddress;

    @Column(name = "industry", length = 200)
    private String industryType;

    @Column(name = "credit")
    private BigDecimal credit;

    @Column(name = "vip", length = 2)
    private String vip;

    @Column(name = "customer_source", length = 200)
    private String customerSource;

    @Column(name = "customer_worker", length = 200)
    private String customerworker;

    @Column(name = "contacts", length = 200)
    private String contacts;

    @Column(name = "duties", length = 200)
    private String duties;

    @Column(name = "receiving_address", length = 200)
    private String receivingAddress;

    @Column(name = "remark", length = 200)
    private String remark;



}
