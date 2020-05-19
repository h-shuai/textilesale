package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.OrderDetailInfo;
import com.rosellete.textilesale.model.OrderStockDetailInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDetailInfoVO extends OrderDetailInfo {
    private String customerName;

    private String customerPhoneNo;

    private String deliveryMode;

    private String contact;

    private String contactPhoneNo;

    private String deliveryAddress;

    private String consignmentDepartment;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    private Double stockedLength;

    private Integer stockedFabricCount;

    private String url;

    private List<OrderStockDetailInfo> orderStockingArrays = new ArrayList<>();

}
