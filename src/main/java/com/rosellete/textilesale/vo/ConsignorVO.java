package com.rosellete.textilesale.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ConsignorVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer consignorNo;

    private String consignor;

    private String consignorPhoneNo;

    private String storageType;

    private String consignorType;

    private String industryType;

    private String consignorAddress;

    private String priority;
}

