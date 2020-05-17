package com.rosellete.textilesale.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FinanceInfoVO {
    private List<Map<String,Object>> accountList;

    private List<Map<String,Object>> selectedRows;
}
