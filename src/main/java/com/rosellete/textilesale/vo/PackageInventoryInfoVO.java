package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.PackageInventoryInfo;
import lombok.Data;

@Data
public class PackageInventoryInfoVO extends PackageInventoryInfo {

    private String recordNo;

    private String storageType;

    private String consignorPhoneNo;

    private String consignorType;

    private String consignor;

    private String industryType;

    private String storageClerkName;
}
