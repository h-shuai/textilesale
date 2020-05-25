package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.business.FinanceInfoBusiness;
import com.rosellete.textilesale.interfaces.FinanceInfoApi;
import com.rosellete.textilesale.model.FinanceLinkOrder;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.FinanceInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/finance")
@Slf4j
public class FinanceInfoController implements FinanceInfoApi {
    @Autowired
    private FinanceInfoBusiness financeInfoBusiness;

    @Override
    public RestResponse saveFinanceInfo(FinanceInfoVO financeInfoVO) {
        return financeInfoBusiness.saveFinanceInfo(financeInfoVO);
    }

    @Override
    public RestResponse getFinanceBatchList(FinanceLinkOrder financeLinkOrder) {
        return financeInfoBusiness.getFinanceBatchList(financeLinkOrder);
    }

    @Override
    public RestResponse getInfoListByBatchNo(String batchNo) {
        return financeInfoBusiness.getInfoListByBatchNo(batchNo);
    }
}
