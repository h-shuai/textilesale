package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.PackageInventoryInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductTypeInfoVO implements Serializable {
    private String productType;
    private String imageName;
    private Double productLength;
    private List<PackageInventoryInfo> packetedStockArrays =new ArrayList<>();
}
