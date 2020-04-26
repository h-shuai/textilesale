package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.OrderDetailInfo;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDetailInfoVO extends OrderDetailInfo {
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

}
