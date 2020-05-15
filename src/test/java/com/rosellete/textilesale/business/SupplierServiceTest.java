package com.rosellete.textilesale.business;

import com.rosellete.textilesale.service.SupplierService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@DisplayName("供应商管理测试用例")
@SpringBootTest
class SupplierServiceTest {

    @Autowired
    private SupplierService supplierService;

    @AfterEach
    void tearDown() {
        supplierService = null;
    }

    @Test
    void getAllByVip(){
        List lsit = supplierService.getAllByVip();
        Assert.assertNotNull(lsit);
    }


}