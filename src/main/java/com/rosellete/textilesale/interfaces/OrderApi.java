package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.OrderInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface OrderApi {
    @PostMapping(value = "/queryOrderList", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getOrderList(@RequestBody OrderInfoVO orderInfoVO);

    @GetMapping(path = "/queryOrderDetail")
    RestResponse getOrderDetailInfo(@RequestParam("orderNo") String orderNo);

    @PostMapping(value = "/queryOrderStockDetail", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getOrderStockDetailInfoByOrderNo(@RequestBody String orderNo);

    @PostMapping(value = "/queryOrderStockDetailByProductType", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getOrderStockDetailInfoByOrderNoAndProductType(@RequestParam("orderNo") String orderNo, @RequestParam("productType") String productType);


}
