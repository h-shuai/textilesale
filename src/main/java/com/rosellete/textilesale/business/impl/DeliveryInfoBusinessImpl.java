package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.DeliveryInfoBusiness;
import com.rosellete.textilesale.model.DeliveryInfo;
import com.rosellete.textilesale.model.PackInfo;
import com.rosellete.textilesale.service.DeliveryInfoService;
import com.rosellete.textilesale.service.PackInfoService;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DeliveryInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class DeliveryInfoBusinessImpl implements DeliveryInfoBusiness {
    @Autowired
    private DeliveryInfoService deliveryInfoService;

    @Autowired
    private PackInfoService packInfoService;

    @Override
    public RestResponse getTodayDepartNum() {
        return new RestResponse(deliveryInfoService.getTodayDepartNum());
    }

    @Override
    public RestResponse saveDeliveryInfo(DeliveryInfoVO deliveryInfoVO) {
        List<String> ids = new ArrayList<>();
        int departNum = deliveryInfoService.getTodayDepartNum() + 1;
        List<DeliveryInfo> deliveryInfos = new ArrayList<>();
        for (Map<String,Object> map : deliveryInfoVO.getSelectedRows()){
            List<PackInfo> packInfos = packInfoService.getPackListByCustomer((String)map.get("customerId"),"1",(String)map.get("businessType"));
            for (PackInfo packInfo : packInfos){
                DeliveryInfo deliveryInfo = new DeliveryInfo();
                deliveryInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                deliveryInfo.setDriverId(deliveryInfoVO.getDriver());
                deliveryInfo.setLicenseId(deliveryInfoVO.getLicense());
                deliveryInfo.setCustomerId(packInfo.getCustomerId());
                deliveryInfo.setCustomerName(packInfo.getCustomerName());
                deliveryInfo.setPackId(packInfo.getId());
                deliveryInfo.setType(deliveryInfoVO.getType());
                deliveryInfo.setCreateTime(new Date());
                deliveryInfo.setTodayDepartNum(departNum);
                deliveryInfo.setRecerveFileUrl((String)map.get("fileName"));
                deliveryInfos.add(deliveryInfo);
                ids.add(packInfo.getId());
            }
        }
        List<String> returnStrs = deliveryInfoService.saveDeliveryInfo(deliveryInfos);
        packInfoService.updateStatusByIds(ids);
        return new RestResponse(returnStrs);
    }

    @Override
    public RestResponse updDeliveryInfo(DeliveryInfoVO deliveryInfoVO) {
        return deliveryInfoService.updDeliveryInfo(deliveryInfoVO);
    }
}
