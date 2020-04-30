package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.OrderDetailInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderStockSaveVO extends OrderDetailInfo {

    private String customerName;

    private String customerPhoneNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;

    private Double orderAmount;

    private String deliveryMode;

    private String deliveryAddress;

    private String contact;

    private String contactPhoneNo;

    private String consignmentDepartment;

    private String customerAddress;

    private String orderStatus;

    private String clerkName;

    private String stocker;

    private Integer reserveTypeCount;

    private Double reserveSumLength;

    private Integer stockedTypeCount;

    private Double stockedSumLength;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stockDate;

    private String deliverer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date delivererDate;

    private List<OrderDetailInfoVO> orderDetailList = new ArrayList<>();

}
