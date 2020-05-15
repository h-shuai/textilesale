package com.rosellete.textilesale.business.impl;

import com.rosellete.textilesale.business.MapperBusiness;
import com.rosellete.textilesale.service.MapperService;
import com.rosellete.textilesale.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class MapperBusinessImpl implements MapperBusiness {
    @Autowired
    private MapperService mapperService;
    @Override
    public RestResponse getAllMapperData() {
        return new RestResponse(mapperService.getAllMapperData());
    }

    @Override
    public RestResponse saveMapperInfo(Map param) {
        return new RestResponse(mapperService.saveMapperInfo(param));
    }
}
