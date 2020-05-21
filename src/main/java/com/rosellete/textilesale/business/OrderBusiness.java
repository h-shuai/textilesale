package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.*;
import org.springframework.beans.support.PagedListHolder;

import java.util.List;

public interface OrderBusiness {
    PageInfo<OrderInfoVO> getOrderList(OrderInfoVO orderInfoVO);

    PageInfo<OrderDetailVO> getOrderStockDetailInfo(String orderNo);

    PageInfo<OrderStockDetailVO> getOrderStockDetailInfo(String orderNo, String productType);

    void confirmOrderStock(String orderNo);

    void orderRestock(String orderNo);

    PageInfo<OrderDetailVO> getOrderDetailList(OrderDetailVO orderDetailInfoVO);

    void saveOrder(OrderSaveVO orderSaveVO);

    void updateOrder(OrderSaveVO orderSaveVO);

    void saveOrderStockDetail(OrderStockSaveVO orderStockSaveVO);

    PagedListHolder<OrderInfoVO> getWaitPackCustomerList(OrderInfoVO orderInfoVO);

    List<String> getTotalCount(Integer orderNo);

    List<PackInfoVO> getPieceList(Integer orderNo);

    RestResponse getWaitSettleList(OrderInfoVO orderInfoVO);

    String copyAndCreateOrder(String orderNo);
}
