package com.rosellete.textilesale.business;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DeliveryInfoVO;

public interface DeliveryInfoBusiness {
    RestResponse getTodayDepartNum();

    RestResponse saveDeliveryInfo(DeliveryInfoVO deliveryInfoVO);
}
