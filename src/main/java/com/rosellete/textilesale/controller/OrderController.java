package com.rosellete.textilesale.controller;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.OrderBusiness;
import com.rosellete.textilesale.interfaces.OrderApi;
import com.rosellete.textilesale.model.OrderStockDetailInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
    public RestResponse getOrderDetailInfo(@RequestParam("orderNo") String orderNo) {
        PageInfo<OrderDetailInfoVO> pageInfo = orderBusiness.getOrderStockDetailInfo(orderNo);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse confirmOrderStock(@RequestParam("orderNo") String orderNo) {
        RestResponse response = new RestResponse();
        try {
            orderBusiness.confirmOrderStock(orderNo);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg("系统内部错误，请稍后重试");
            log.error("订单{}备货完成确认失败", orderNo, e);
        }
        return response;
    }

    @Override
    public RestResponse orderRestock(@RequestParam("orderNo") String orderNo) {
        RestResponse response = new RestResponse();
        try {
            orderBusiness.orderRestock(orderNo);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg("系统内部错误，请稍后重试");
            log.error("订单{}提交重新备货失败", orderNo, e);
        }
        return response;
    }

    @Override
    public RestResponse getOrderStockDetailInfo(String orderNo, String productType) {
        PageInfo<OrderStockDetailInfoVO> pageInfo = orderBusiness.getOrderStockDetailInfo(orderNo,productType);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse getOrderDetailList(OrderDetailInfoVO orderDetailInfoVO) {
        PageInfo<OrderDetailInfoVO> pageInfo = orderBusiness.getOrderDetailList(orderDetailInfoVO);
        return new RestResponse(pageInfo);
    }

    @Override
    public RestResponse saveOrder(@RequestBody @Valid OrderSaveVO orderSaveVO) {

        RestResponse response = new RestResponse();
        try {
            orderBusiness.saveOrder(orderSaveVO);
        } catch (Exception e) {
            response.setCode(999);
            response.setMsg("系统内部错误，请稍后重试");
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
            response.setMsg("系统内部错误，请稍后重试");
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
            response.setMsg("系统内部错误，请稍后重试");
            log.error("保存订单备货数据{}失败", orderStockSaveVO, e);
        }
        return response;

    }
}
