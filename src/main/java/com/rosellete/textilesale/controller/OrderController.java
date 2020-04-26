package com.rosellete.textilesale.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.OrderBusiness;
import com.rosellete.textilesale.interfaces.OrderApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.OrderInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        OrderInfoVO orderInfo = orderBusiness.getOrderDetailInfo(orderNo);
        return new RestResponse(JSONObject.toJSONString(orderInfo));
    }

    @Override
    public RestResponse getOrderStockDetailInfoByOrderNo(@RequestBody String orderNo) {
        OrderInfoVO orderInfo = orderBusiness.getOrderStockDetailInfo(orderNo);
        return new RestResponse(orderInfo);
    }

    @Override
    public RestResponse getOrderStockDetailInfoByOrderNoAndProductType(@RequestBody String orderNo, @RequestBody String productType) {
        OrderInfoVO orderInfo = orderBusiness.getOrderStockDetailInfo(orderNo,productType);
        return new RestResponse(orderInfo);
    }

}
