package com.rosellete.textilesale.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PackDetailInfoVO {
    private String orderNo;

    private int productCount;

    private int pieceCount;

    private Double riceCount;

    private List<Map<String,Object>> productInfoList;

    private String id;

    private Integer status;

    private String packPic;

    private Integer packNo;

    private List<PackInfoVO> packInfoVOS;
}
