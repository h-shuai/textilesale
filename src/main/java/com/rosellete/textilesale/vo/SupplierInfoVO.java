package com.rosellete.textilesale.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupplierInfoVO implements Serializable {
    private Integer supplierNo;

    private String supplierName;

    private String supplierPhoneNo;

    private String supplierType;

    private String supplierAddress;

    private String industryType;

    private String priority;

}
