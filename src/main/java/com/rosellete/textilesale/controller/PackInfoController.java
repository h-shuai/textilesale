package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.business.PackInfoBusiness;
import com.rosellete.textilesale.interfaces.PackInfoApi;
import com.rosellete.textilesale.model.PackInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.PackDetailInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/packinfo")
@Slf4j
public class PackInfoController implements PackInfoApi {
    @Autowired
    private PackInfoBusiness packInfoBusiness;

    @Override
    public RestResponse getPackListByCustomer(String customer,String businessType) {
        return packInfoBusiness.getPackListByCustomer(customer,businessType);
    }

    @Override
    public RestResponse getPackDetails(String id) {
        return packInfoBusiness.getPackDetails(id);
    }

    @Override
    public RestResponse savePackInfo(@Valid PackDetailInfoVO packDetailInfoVO) {
        return packInfoBusiness.savePackInfo(packDetailInfoVO);
    }

    @Override
    public RestResponse updatePackInfo(PackDetailInfoVO packDetailInfoVO) {
        return packInfoBusiness.updatePackInfo(packDetailInfoVO);
    }

    @Override
    public RestResponse deletePackInfoById(String id) {
        return packInfoBusiness.deletePackInfoById(id);
    }

    @Override
    public RestResponse getWaitDeliveryList(PackInfo packInfo) {
        return new RestResponse(packInfoBusiness.getWaitDeliveryList(packInfo));
    }
}
