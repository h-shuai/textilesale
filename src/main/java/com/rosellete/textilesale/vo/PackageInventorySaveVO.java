package com.rosellete.textilesale.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PackageInventorySaveVO implements Serializable {
    private Integer consignorNo;

    private String consignorType;

    private String consignor;

    private List<StoragePackageVO> packageList =new ArrayList<>();
}
