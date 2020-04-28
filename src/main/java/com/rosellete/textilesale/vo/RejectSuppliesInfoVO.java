package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.RejectSuppliesInfo;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class RejectSuppliesInfoVO extends RejectSuppliesInfo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rejectedDate;

    @Column(name = "supplier_phone_no", length = 15)
    private String supplierPhoneNo;

    @Column(name = "supplier_type",length = 1)
    private String supplierType;

    @Column(name = "supplier_name",length = 200)
    private String supplierName;

    @Column(name = "industry_type", length = 1)
    private String industryType;
}
