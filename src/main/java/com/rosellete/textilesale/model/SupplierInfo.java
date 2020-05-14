package com.rosellete.textilesale.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "t_supplier_info")
@Data
public class SupplierInfo extends Page implements Serializable{
    @Id
    @Column(name = "supplier_no", nullable = false, length = 30)
    private Integer supplierNo;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "short_name", length = 200)
    private String shortName;

    @Column(name = "phone", length = 200)
    private String phone;

    @Column(name = "type", length = 200)
    private String type;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "industry", length = 200)
    private String industry;

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
