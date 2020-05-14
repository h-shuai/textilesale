package com.rosellete.textilesale.business;

import com.rosellete.textilesale.model.DeliveryInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DeliveryInfoVO;

import java.util.List;

public interface DeliveryInfoBusiness {
    RestResponse getTodayDepartNum();

    RestResponse saveDeliveryInfo(DeliveryInfoVO deliveryInfoVO);
}
