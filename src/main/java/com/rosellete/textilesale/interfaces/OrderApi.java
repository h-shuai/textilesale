package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.OrderDetailVO;
import com.rosellete.textilesale.vo.OrderInfoVO;
import com.rosellete.textilesale.vo.OrderSaveVO;
import com.rosellete.textilesale.vo.OrderStockSaveVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface OrderApi {
    @PostMapping(value = "/queryOrderList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getOrderList(@RequestBody @Valid OrderInfoVO orderInfoVO);

    @GetMapping(path = "/queryOrderDetail")
    RestResponse getOrderDetailInfo(@RequestParam("orderNo") Integer orderNo);

    @GetMapping(path = "/confirmOrderStock")
    RestResponse confirmOrderStock(@RequestParam("orderNo") Integer orderNo);

    @GetMapping(path = "/restock")
    RestResponse orderRestock(@RequestParam("orderNo") Integer orderNo);

    @GetMapping(path = "/copyAndCreateOrder")
    RestResponse copyAndCreateOrder(@RequestParam("orderNo") Integer orderNo);

    @GetMapping(path = "/queryOrderStockDetail")
    RestResponse getOrderStockDetailInfo(@RequestParam("orderNo") Integer orderNo, @RequestParam("productType") String productType);

    @PostMapping(value = "/queryOrderDetailList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getOrderDetailList(@RequestBody @Valid OrderDetailVO orderDetailInfoVO);

    @PostMapping(value = "/saveOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveOrder(@RequestBody @Valid OrderSaveVO orderSaveVO);

    @PostMapping(value = "/updateOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse updateOrder(@RequestBody @Valid OrderSaveVO orderSaveVO);

    @PostMapping(value = "/saveOrderStockDetail", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveOrderStockDetail(@RequestBody @Valid OrderStockSaveVO orderStockSaveVO);

    @PostMapping(value = "/getWaitPackCustomerList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getWaitPackCustomerList(@RequestBody OrderInfoVO orderInfoVO);

    @RequestMapping("/getTotalCount")
    RestResponse getTotalCount(@RequestParam("customer") Integer customer,@RequestParam("businessType") String businessType);

    @PostMapping(value = "/getPieceList",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getPieceList(@RequestBody OrderInfoVO orderInfoVO);

    @PostMapping(value = "/getWaitSettleList",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getWaitSettleList(@RequestBody OrderInfoVO orderInfoVO);
}
