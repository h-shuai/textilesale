package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.business.CustomerBusiness;
import com.rosellete.textilesale.interfaces.CustomerApi;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.CustomerInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController implements CustomerApi {
    @Autowired
    private CustomerBusiness customerBusiness;

    @Override
    public RestResponse getAllCustomer() {
        return null;
    }

    public RestResponse createCustomer(@RequestBody @Valid CustomerInfoVO customerInfoVO){
        log.info("保存客户信息");
        customerBusiness.save(customerInfoVO);
        return new RestResponse();
    }

    @Override
    public RestResponse getCustomer(@Valid CustomerInfoVO customerInfoVO) {
        return new RestResponse(customerBusiness.findAllCustomer(customerInfoVO));
    }

    @Override
    public RestResponse viewCustomer(String customerNo) {
        return new RestResponse(customerBusiness.findByCustomerNo(Integer.parseInt(customerNo)));
    }
}
