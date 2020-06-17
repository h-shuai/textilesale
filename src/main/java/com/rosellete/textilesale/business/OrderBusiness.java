package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.*;
import org.springframework.beans.support.PagedListHolder;

import java.util.List;

public interface OrderBusiness {
    PageInfo<OrderInfoVO> getOrderList(OrderInfoVO orderInfoVO);

    PageInfo<OrderDetailVO> getOrderStockDetailInfo(Integer orderNo);

    PageInfo<OrderStockDetailVO> getOrderStockDetailInfo(Integer orderNo, String productType);

    void confirmOrderStock(Integer orderNo);

    void orderRestock(Integer orderNo);

    PageInfo<OrderDetailVO> getOrderDetailList(OrderDetailVO orderDetailInfoVO);

    void saveOrder(OrderSaveVO orderSaveVO);

    void updateOrder(OrderSaveVO orderSaveVO);

    void saveOrderStockDetail(OrderStockSaveVO orderStockSaveVO);

    PagedListHolder<OrderInfoVO> getWaitPackCustomerList(OrderInfoVO orderInfoVO);

    PackInfoVO getTotalCount(Integer orderNo,String businessType);

    List<PackInfoVO> getPieceList(Integer orderNo,String businessType,String address);

    RestResponse getWaitSettleList(OrderInfoVO orderInfoVO);

    Integer copyAndCreateOrder(Integer orderNo);

    Integer getSequenceNo();
}
