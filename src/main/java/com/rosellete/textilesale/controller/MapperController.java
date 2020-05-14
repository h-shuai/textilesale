package com.rosellete.textilesale.controller;

import com.rosellete.textilesale.business.MapperBusiness;
import com.rosellete.textilesale.interfaces.MapperApi;
import com.rosellete.textilesale.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/mapper")
@Slf4j
public class MapperController implements MapperApi {
    @Autowired
    private MapperBusiness mapperBusiness;
    @Override
    public RestResponse getAllMapperData() {
        return mapperBusiness.getAllMapperData();
    }

    @Override
    public RestResponse saveMapperInfo(Map param) {
        return mapperBusiness.saveMapperInfo(param);
    }
}
