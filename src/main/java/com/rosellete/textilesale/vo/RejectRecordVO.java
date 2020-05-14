package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.RejectRecord;
import lombok.Data;

import java.util.Date;

@Data
public class RejectRecordVO extends RejectRecord {

    private String supplierName;

    private String supplierType;

    private String industryType;

    private String supplierPhoneNo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
}
