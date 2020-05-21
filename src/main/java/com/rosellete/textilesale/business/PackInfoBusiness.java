package com.rosellete.textilesale.business;

import com.rosellete.textilesale.model.PackInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.PackDetailInfoVO;
import com.rosellete.textilesale.vo.PackInfoVO;
import org.springframework.beans.support.PagedListHolder;

public interface PackInfoBusiness {
    RestResponse getPackListByCustomer(String customer);

    RestResponse getPackDetails(String id);

    RestResponse savePackInfo(PackDetailInfoVO packDetailInfoVO);

    RestResponse updatePackInfo(PackDetailInfoVO packDetailInfoVO);

    RestResponse deletePackInfoById(String id);

    PagedListHolder<PackInfoVO> getWaitDeliveryList(PackInfo packInfo);
}
