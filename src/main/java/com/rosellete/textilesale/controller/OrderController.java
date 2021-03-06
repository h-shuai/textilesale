package com.rosellete.textilesale.controller;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.OrderBusiness;
import com.rosellete.textilesale.interfaces.OrderApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController implements OrderApi {
    @Autowired
    private OrderBusiness orderBusiness;

    @Override
    public RestResponse getOrderList(@RequestBody OrderInfoVO orderInfoVO) {
        PageInfo<OrderInfoVO> pageInfo = orderBusiness.getOrderList(orderInfoVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getOrderDetailInfo(@RequestParam("orderNo") Integer orderNo) {
        PageInfo<OrderDetailVO> pageInfo = orderBusiness.getOrderStockDetailInfo(orderNo);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse confirmOrderStock(@RequestParam("orderNo") Integer orderNo) {
        RestResponse response = new RestResponse();

        try {
            orderBusiness.confirmOrderStock(orderNo);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg(null==e.getMessage()?"系统内部错误，请稍后重试":e.getMessage());
            log.error("订单{}备货完成确认失败", orderNo, e);
        }
        return response;
    }

    @Override
    public RestResponse orderRestock(@RequestParam("orderNo") Integer orderNo) {
        RestResponse response = new RestResponse();
        try {
            orderBusiness.orderRestock(orderNo);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg(null==e.getMessage()?"系统内部错误，请稍后重试":e.getMessage());
            log.error("订单{}提交重新备货失败", orderNo, e);
        }
        return response;
    }

    @Override
    public RestResponse copyAndCreateOrder(Integer orderNo) {
        RestResponse response = new RestResponse();
        Integer newOrderNo;
        try {
            newOrderNo= orderBusiness.copyAndCreateOrder(orderNo);
        } catch (Exception e) {
            newOrderNo=null;
            response.setCode(999);
            response.setMsg(null==e.getMessage()?"系统内部错误，请稍后重试":e.getMessage());
            log.error("订单{}备货完成确认失败", orderNo, e);
        }
        response.setData(newOrderNo);
        return response;
    }

    @Override
    public RestResponse getOrderStockDetailInfo(Integer orderNo, String productType) {
        PageInfo<OrderStockDetailVO> pageInfo = orderBusiness.getOrderStockDetailInfo(orderNo, productType);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getOrderDetailList(OrderDetailVO orderDetailInfoVO) {
        PageInfo<OrderDetailVO> pageInfo = orderBusiness.getOrderDetailList(orderDetailInfoVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse saveOrder(@RequestBody @Valid OrderSaveVO orderSaveVO) {

        RestResponse response = new RestResponse();
        try {
            orderBusiness.saveOrder(orderSaveVO);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg(null==e.getMessage()?"系统内部错误，请稍后重试":e.getMessage());
            log.error("保存订单数据{}失败", orderSaveVO, e);
        }
        return response;
    }

    @Override
    public RestResponse updateOrder(@Valid OrderSaveVO orderSaveVO) {
        RestResponse response = new RestResponse();
        try {
            orderBusiness.updateOrder(orderSaveVO);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg(null==e.getMessage()?"系统内部错误，请稍后重试":e.getMessage());
            log.error("更新订单数据{}失败", orderSaveVO, e);
        }
        return response;
    }

    @Override
    public RestResponse saveOrderStockDetail(@Valid OrderStockSaveVO orderStockSaveVO) {
        RestResponse response = new RestResponse();
        try {
            orderBusiness.saveOrderStockDetail(orderStockSaveVO);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg(null==e.getMessage()?"系统内部错误，请稍后重试":e.getMessage());
            log.error("保存订单备货数据{}失败", orderStockSaveVO, e);
        }
        return response;

    }

    @Override
    public RestResponse getWaitPackCustomerList(OrderInfoVO orderInfoVO) {
        return new RestResponse(orderBusiness.getWaitPackCustomerList(orderInfoVO));
    }

    @Override
    public RestResponse getTotalCount(Integer customer,String businessType) {
        return new RestResponse(orderBusiness.getTotalCount(customer,businessType));
    }

    @Override
    public RestResponse getPieceList(@RequestBody OrderInfoVO orderInfoVO) {
        return new RestResponse(orderBusiness.getPieceList(orderInfoVO.getCustomer(),orderInfoVO.getBusinessType(),orderInfoVO.getAddress()));
    }

    @Override
    public RestResponse getWaitSettleList(OrderInfoVO orderInfoVO) {
        return orderBusiness.getWaitSettleList(orderInfoVO);
    }
}
