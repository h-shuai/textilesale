package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.OrderDetailInfoVO;
import com.rosellete.textilesale.vo.OrderInfoVO;
import com.rosellete.textilesale.vo.OrderSaveVO;
import com.rosellete.textilesale.vo.OrderStockSaveVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface OrderApi {
    @PostMapping(value = "/queryOrderList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getOrderList(@RequestBody OrderInfoVO orderInfoVO);

    @GetMapping(path = "/queryOrderDetail")
    RestResponse getOrderDetailInfo(@RequestParam("orderNo") String orderNo);

    @PutMapping(path = "/confirmOrderStock")
    RestResponse confirmOrderStock(@RequestParam("orderNo") String orderNo);

    @PutMapping(path = "/restock")
    RestResponse orderRestock(@RequestParam("orderNo") String orderNo);

    @GetMapping(path = "/queryOrderStockDetail")
    RestResponse getOrderStockDetailInfo(@RequestParam("orderNo") String orderNo,@RequestParam("productType") String productType);

    @PostMapping(value = "/queryOrderDetailList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getOrderDetailList(@RequestBody OrderDetailInfoVO orderDetailInfoVO);

    @PostMapping(value = "/saveOrder",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveOrder(@RequestBody @Valid OrderSaveVO orderSaveVO);

    @PostMapping(value = "/updateOrder",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse updateOrder(@RequestBody @Valid OrderSaveVO orderSaveVO);

    @PostMapping(value = "/saveOrderStockDetail",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveOrderStockDetail(@RequestBody @Valid OrderStockSaveVO orderStockSaveVO);
}
