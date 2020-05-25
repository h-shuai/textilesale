package com.rosellete.textilesale.business;

import com.rosellete.textilesale.model.FinanceLinkOrder;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.FinanceInfoVO;

public interface FinanceInfoBusiness {
    RestResponse saveFinanceInfo(FinanceInfoVO financeInfoVO);

    RestResponse getFinanceBatchList(FinanceLinkOrder financeLinkOrder);

    RestResponse getInfoListByBatchNo(String batchNo);
}
