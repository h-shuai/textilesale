package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface MapperApi {
    @GetMapping(value = "/getAllMapperData")
    RestResponse getAllMapperData(@RequestParam("type") String type);

    @PostMapping(value = "/saveMapperInfo",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveMapperInfo(@RequestBody Map param);
}
