package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.business.AccountBusiness;
import com.rosellete.textilesale.interfaces.AccountApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.AccountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController implements AccountApi {
    @Autowired
    private AccountBusiness accountBusiness;

    @Override
    public RestResponse getPageByCustomer(@RequestBody AccountVO accountVO) {
        return accountBusiness.getPageByCustomer(accountVO);
    }

    @Override
    public RestResponse saveAccountInfo(@RequestBody AccountVO accountVO) {
        return accountBusiness.saveAccountInfo(accountVO);
    }

    @Override
    public RestResponse getDetailPage(@RequestBody AccountVO accountVO) {
        return accountBusiness.getDetailPage(accountVO);
    }
}
