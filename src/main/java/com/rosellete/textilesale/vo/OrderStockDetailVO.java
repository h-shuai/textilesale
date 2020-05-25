package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.OrderStockDetail;
import lombok.Data;

import java.util.Date;

@Data
public class OrderStockDetailVO extends OrderStockDetail {
    private String customerName;

    private String customerPhoneNo;

    private String deliveryMode;

    private String contact;

    private String contactPhoneNo;

    private String deliveryAddress;

    private String consignmentDepartment;

    private Double productLength;

    private String extraCrafts;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
}
