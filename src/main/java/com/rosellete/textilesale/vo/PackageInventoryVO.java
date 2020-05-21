package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.PackageInventory;
import lombok.Data;

@Data
public class PackageInventoryVO extends PackageInventory {

    private String recordNo;

    private String storageType;

    private String consignorPhoneNo;

    private String consignorType;

    private String consignor;

    private String industryType;

    private String storageClerkName;
}
