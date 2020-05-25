package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.OrderDetailInfo;
import com.rosellete.textilesale.model.OrderStockDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDetailVO extends OrderDetailInfo {
    private Integer customeNo;
    private String customerName;

    private String customerPhoneNo;
    private String customerAddress;

    private String customerType;
    private String industryType;

    private String deliveryMode;

    private String liaison;

    private String contact;

    private String deliveryAddress;

    private String consignmentDepartment;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    private Double stockedLength;

    private Integer stockedFabricCount;

    private String url;

    private List<OrderStockDetail> orderStockingArrays = new ArrayList<>();

}
