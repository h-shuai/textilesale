package com.rosellete.textilesale.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PackInfoVO {
    private String picurl;

    private String colthModel;

    private List<Map<String,Object>> pieceOptions;
}
