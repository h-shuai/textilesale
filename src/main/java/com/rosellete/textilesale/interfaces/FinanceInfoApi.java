package com.rosellete.textilesale.interfaces;


import com.rosellete.textilesale.model.FinanceLinkOrder;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.FinanceInfoVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface FinanceInfoApi {
    @PostMapping(value = "/saveFinanceInfo",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveFinanceInfo(@RequestBody FinanceInfoVO financeInfoVO);

    @PostMapping(value = "/getFinanceBatchList",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getFinanceBatchList(@RequestBody FinanceLinkOrder financeLinkOrder);

    @GetMapping(value = "/getInfoListByBatchNo")
    RestResponse getInfoListByBatchNo(@RequestParam("batchNo") String batchNo);
}
