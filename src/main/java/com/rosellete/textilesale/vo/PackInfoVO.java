package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.PackInfo;
import lombok.Data;

import java.util.List;

@Data
public class PackInfoVO extends PackInfo {
    private Integer orderNo;

    private List<PackSubInfoVO> packSubInfoVOS;

    private Integer packNum;

    private String consignDep;
}
