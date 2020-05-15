package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.business.CustomerBusiness;
import com.rosellete.textilesale.interfaces.CustomerApi;
import com.rosellete.textilesale.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController implements CustomerApi {
    @Autowired
    private CustomerBusiness customerBusiness;

    @Override
    public RestResponse getAllCustomer() {
        return null;
    }
}
