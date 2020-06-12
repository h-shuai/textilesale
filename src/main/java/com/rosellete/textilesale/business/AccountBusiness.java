package com.rosellete.textilesale.business;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.AccountVO;

public interface AccountBusiness {
    RestResponse getPageByCustomer(AccountVO accountVO);

    RestResponse saveAccountInfo(AccountVO accountVO);

    RestResponse getDetailPage(AccountVO accountVO);
}
