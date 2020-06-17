package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.model.DeliveryInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DeliveryInfoVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface DeliveryInfoApi {
    @GetMapping(value = "/getTodayDepartNum")
    RestResponse getTodayDepartNum();

    @PostMapping(value = "/saveDeliveryInfo",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveDeliveryInfo(@RequestBody DeliveryInfoVO deliveryInfoVO);

    @PostMapping(value = "/updDeliveryInfo",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse updDeliveryInfo(@RequestBody DeliveryInfoVO deliveryInfoVO);
}
