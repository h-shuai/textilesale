package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.vo.OrderDetailInfoVO;
import com.rosellete.textilesale.vo.OrderInfoVO;
import com.rosellete.textilesale.vo.OrderSaveVO;
import com.rosellete.textilesale.vo.OrderStockDetailInfoVO;

public interface OrderBusiness {
    PageInfo<OrderInfoVO> getOrderList(OrderInfoVO orderInfoVO);

    PageInfo<OrderDetailInfoVO> getOrderStockDetailInfo(String orderNo);

    PageInfo<OrderStockDetailInfoVO> getOrderStockDetailInfo(String orderNo, String productType);

    void confirmOrderStock(String orderNo);

    void orderRestock(String orderNo);

    PageInfo<OrderDetailInfoVO> getOrderDetailList(OrderDetailInfoVO orderDetailInfoVO);

    void saveOrder(OrderSaveVO orderSaveVO);
}
