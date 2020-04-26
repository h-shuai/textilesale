package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.OrderInfo;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoVO extends OrderInfo {
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
}
