package com.rosellete.textilesale.vo;

import com.rosellete.textilesale.model.RejectRecord;
import com.rosellete.textilesale.model.RejectSuppliesInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RejectRecordSaveVO extends RejectRecord {

    private List<ProductTypeInfoVO> productTypeList = new ArrayList<>();
}
