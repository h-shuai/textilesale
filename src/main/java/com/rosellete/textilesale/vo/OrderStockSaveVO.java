package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.OrderDetailInfo;
import com.rosellete.textilesale.model.OrderStockDetailInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderStockSaveVO extends OrderDetailInfo {
    private String orderNo;
    private String productType;
    private List<OrderStockDetailInfo> orderStockingArrays=new ArrayList<>();

}
