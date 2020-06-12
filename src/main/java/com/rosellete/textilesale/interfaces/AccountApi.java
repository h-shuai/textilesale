package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.AccountVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AccountApi {
    @PostMapping(value = "/getPageByCustomer",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getPageByCustomer(@RequestBody AccountVO accountVO);

    @PostMapping(value = "/saveAccountInfo",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveAccountInfo(@RequestBody AccountVO accountVO);

    @PostMapping(value = "/getDetailPage",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getDetailPage(@RequestBody AccountVO accountVO);
}
