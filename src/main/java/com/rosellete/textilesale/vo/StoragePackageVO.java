package com.rosellete.textilesale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosellete.textilesale.model.StoragePackageInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class StoragePackageVO extends StoragePackageInfo {

    private String storageType;

    private String consignorNo;

    private String consignorType;

    private String consignor;

    private String industryType;

    private String storageClerkName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date storageDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    private List<ProductTypeInfoVO> productTypeList=new ArrayList<>();

}
