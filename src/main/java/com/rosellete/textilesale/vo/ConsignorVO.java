package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.Page;
import lombok.Data;

import java.io.Serializable;
@Data
public class ConsignorVO extends Page implements Serializable {

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

