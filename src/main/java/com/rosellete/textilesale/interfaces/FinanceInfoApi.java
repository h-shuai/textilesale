package com.rosellete.textilesale.interfaces;


import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.FinanceInfoVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FinanceInfoApi {
    @PostMapping(value = "/saveFinanceInfo",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveFinanceInfo(@RequestBody FinanceInfoVO financeInfoVO);
}
