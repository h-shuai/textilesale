package com.rosellete.textilesale.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.FinanceInfoBusiness;
import com.rosellete.textilesale.model.FinanceLinkOrder;
import com.rosellete.textilesale.service.FinanceInfoService;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.FinanceInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Override
    public RestResponse getFinanceBatchList(FinanceLinkOrder financeLinkOrder) {
        return new RestResponse(financeInfoService.getFinanceBatchList(financeLinkOrder));
    }

    @Override
    public RestResponse getInfoListByBatchNo(String batchNo) {
        return new RestResponse(financeInfoService.getInfoListByBatchNo(batchNo));
    }
}
