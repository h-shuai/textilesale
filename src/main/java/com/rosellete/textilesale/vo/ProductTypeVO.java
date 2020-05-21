package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.PackageInventory;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductTypeVO implements Serializable {
    private String productType;
    private String imageName;
    private Double productLength;
    private List<PackageInventory> packetedStockArrays =new ArrayList<>();
}
