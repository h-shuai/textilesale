package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.RejectSupplies;
import lombok.Data;

import java.util.Date;

@Data
public class RejectSuppliesVO extends RejectSupplies {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rejectedDate;

    private String supplierNo;

    private String supplierPhoneNo;

    private String supplierType;

    private String supplierName;

    private String industryType;

    private String url;
}
