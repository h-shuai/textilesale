package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.AccountBusiness;
import com.rosellete.textilesale.service.AccountService;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBusinessImpl implements AccountBusiness {
    @Autowired
    private AccountService accountService;

    @Override
    public RestResponse getPageByCustomer(AccountVO accountVO) {
        return new RestResponse(accountService.getPageByCustomer(accountVO));
    }

    @Override
    public RestResponse saveAccountInfo(AccountVO accountVO) {
        accountService.saveAccountInfo(accountVO);
        return new RestResponse();
    }

    @Override
    public RestResponse getDetailPage(AccountVO accountVO) {
        return new RestResponse(accountService.getDetailPage(accountVO));
    }
}
