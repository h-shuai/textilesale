package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.OrderInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderStockSaveVO extends OrderInfo {

    private Integer reserveTypeCount;

    private Double reserveSumLength;

    private Integer stockedTypeCount;

    private Double stockedSumLength;

    private List<OrderDetailVO> orderDetailList = new ArrayList<>();

}
