package com.rosellete.textilesale.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerInfoVO implements Serializable {
    private Integer customerNo;

    private String customerName;

    private String customerPhoneNo;

    private String customerType;

    private String customerAddress;

    private String industryType;

    private String priority;

}
