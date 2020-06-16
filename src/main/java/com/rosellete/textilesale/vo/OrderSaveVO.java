package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.OrderDetailInfo;
import com.rosellete.textilesale.model.OrderInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderSaveVO extends OrderInfo {
    private String customerName;
    private String customerPhoneNo;
    private List<OrderDetailInfo> orderDetailList = new ArrayList<>();
}
