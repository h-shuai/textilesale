package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.OrderInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class OrderInfoVO extends OrderInfo {
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    private Integer stockedTypeCount;

    private Double stockedSumLength;

    private String orderCount;

    private String customerName;

    private String customerType;

    private String customerPhoneNo;

    private String industryType;

    private List<Map<String,Object>> selectedRows;
}
