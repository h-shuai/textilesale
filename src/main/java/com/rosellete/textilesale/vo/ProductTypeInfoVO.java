package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.PackageInventoryInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductTypeInfoVO implements Serializable {
    private String productType;
    private Double productLength;
    List<PackageInventoryInfo> packetedStockArrays =new ArrayList<>();
}
