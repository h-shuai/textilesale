package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.FinanceInfoBusiness;
import com.rosellete.textilesale.service.FinanceInfoService;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.FinanceInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FinanceInfoBusinessImpl implements FinanceInfoBusiness {
    @Autowired
    private FinanceInfoService financeInfoService;

    @Override
    public RestResponse saveFinanceInfo(FinanceInfoVO financeInfoVO) {
        financeInfoService.saveFinanceInfo(financeInfoVO);
        return new RestResponse();
    }
}
