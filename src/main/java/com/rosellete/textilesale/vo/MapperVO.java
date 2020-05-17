package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.CosignMapper;
import com.rosellete.textilesale.model.DriverMapper;
import com.rosellete.textilesale.model.LicenseMapper;
import com.rosellete.textilesale.model.PaymethodMapper;
import lombok.Data;

import java.util.List;

@Data
public class MapperVO {
    private List<DriverMapper> driverMappers;

    private List<LicenseMapper> licenseMappers;

    private List<CosignMapper> cosignMappers;

    private List<PaymethodMapper> paymethodMappers;
}
