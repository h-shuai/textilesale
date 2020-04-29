package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.model.OrderInfo;
import com.rosellete.textilesale.vo.*;

import java.util.List;
import java.util.Map;

public interface OrderBusiness {
    PageInfo<OrderInfoVO> getOrderList(OrderInfoVO orderInfoVO);

    PageInfo<OrderDetailInfoVO> getOrderStockDetailInfo(String orderNo);

    PageInfo<OrderStockDetailInfoVO> getOrderStockDetailInfo(String orderNo, String productType);

    void confirmOrderStock(String orderNo);

    void orderRestock(String orderNo);

    PageInfo<OrderDetailInfoVO> getOrderDetailList(OrderDetailInfoVO orderDetailInfoVO);

    void saveOrder(OrderSaveVO orderSaveVO);

    void updateOrder(OrderSaveVO orderSaveVO);

    void saveOrderStockDetail(OrderStockSaveVO orderStockSaveVO);

    PageInfo<OrderInfoVO> getWaitPackOrderList(OrderInfo orderInfo);

    List<String> getTotalCount(String orderNo);

    List<PackInfoVO> getPieceList(String orderNo);
}
