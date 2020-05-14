package com.rosellete.textilesale.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PackSubInfoVO {
    private String picurl;

    private String colthModel;

    private List<Map<String,Object>> pieceOptions;
}
