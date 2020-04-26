package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.vo.OrderInfoVO;

public interface OrderBusiness {
    PageInfo<OrderInfoVO> getOrderList(OrderInfoVO orderInfoVO);

    OrderInfoVO getOrderDetailInfo(String orderNo);

    OrderInfoVO getOrderDetailInfo(String orderNo,String productType);

    OrderInfoVO getOrderStockDetailInfo(String orderNo);

    OrderInfoVO getOrderStockDetailInfo(String orderNo,String productType);
}
