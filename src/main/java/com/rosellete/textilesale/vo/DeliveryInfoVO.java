package com.rosellete.textilesale.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DeliveryInfoVO {
    private Integer type;

    private String driver;

    private String license;

    private List<Map<String,Object>> selectedRows;

    private Integer isSelf;

    private List<String> ids;

    private String fileNames;
}
