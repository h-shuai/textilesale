package com.rosellete.textilesale.business;

import com.rosellete.textilesale.util.RestResponse;

import java.util.Map;

public interface MapperBusiness {
    RestResponse getAllMapperData(String type);

    RestResponse saveMapperInfo(Map param);
}
