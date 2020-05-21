package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.*;
import org.springframework.beans.support.PagedListHolder;

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

    PagedListHolder<OrderInfoVO> getWaitPackCustomerList(OrderInfoVO orderInfoVO);

    List<String> getTotalCount(Integer orderNo);

    List<PackInfoVO> getPieceList(Integer orderNo);

    RestResponse getWaitSettleList(OrderInfoVO orderInfoVO);
}
