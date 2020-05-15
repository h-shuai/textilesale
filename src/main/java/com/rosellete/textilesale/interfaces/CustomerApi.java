package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;

public interface CustomerApi {

    @GetMapping(path = "/getAllCustomer")
    RestResponse getAllCustomer();


}
