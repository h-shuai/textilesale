package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.CustomerInfoVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface CustomerApi {

    @GetMapping(path = "/getAllCustomer")
    RestResponse getAllCustomer();

    @PostMapping(value = "/createCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse createCustomer(@RequestBody @Valid CustomerInfoVO customerInfoVO);

    @PostMapping(value = "/getCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getCustomer(@RequestBody @Valid CustomerInfoVO customerInfoVO);

    @GetMapping(value = "/viewCustomer")
    RestResponse viewCustomer(@RequestParam("customerNo") String customerNo);


}
