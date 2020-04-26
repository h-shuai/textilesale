package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.OrderDetailInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDetailInfoVO extends OrderDetailInfo {
    private List<OrderStockDetailInfoVO> orderStockDetailInfoVOList = new ArrayList<>();
}
