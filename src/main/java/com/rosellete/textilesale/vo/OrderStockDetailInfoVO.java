package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.OrderStockDetailInfo;
import lombok.Data;

import java.util.Date;

@Data
public class OrderStockDetailInfoVO extends OrderStockDetailInfo {
    private String customerName;
    private String customerPhoneNo;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private String deliveryMode;
    private String contact;
    private String contactPhoneNo;;
    private String deliveryAddress;
    private String consignmentDepartment;
    private String productType;
    private Double productLength;
    private String extraCrafts;
}
