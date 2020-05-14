package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.business.DeliveryInfoBusiness;
import com.rosellete.textilesale.interfaces.DeliveryInfoApi;
import com.rosellete.textilesale.model.DeliveryInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DeliveryInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delivery")
@Slf4j
public class DeliveryInfoController implements DeliveryInfoApi {
    @Autowired
    private DeliveryInfoBusiness deliveryInfoBusiness;

    @Override
    public RestResponse getTodayDepartNum() {
        return deliveryInfoBusiness.getTodayDepartNum();
    }

    @Override
    public RestResponse saveDeliveryInfo(DeliveryInfoVO deliveryInfoVO) {
        return deliveryInfoBusiness.saveDeliveryInfo(deliveryInfoVO);
    }
}
