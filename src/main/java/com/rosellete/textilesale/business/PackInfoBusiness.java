package com.rosellete.textilesale.business;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.PackDetailInfoVO;

public interface PackInfoBusiness {
    RestResponse getPackDetails(String id);

    RestResponse savePackInfo(PackDetailInfoVO packDetailInfoVO);

    RestResponse updatePackInfo(PackDetailInfoVO packDetailInfoVO);

    RestResponse deletePackInfoById(String id);
}
