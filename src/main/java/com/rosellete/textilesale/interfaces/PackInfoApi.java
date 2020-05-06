package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.PackDetailInfoVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface PackInfoApi {
    @GetMapping(value = "/getPackDetails")
    RestResponse getPackDetails(@RequestParam("id") String id);

    @PostMapping(value = "/savePackInfo",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse savePackInfo(@RequestBody @Valid PackDetailInfoVO packDetailInfoVO);

    @PostMapping(value = "/updatePackInfo",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse updatePackInfo(@RequestBody PackDetailInfoVO packDetailInfoVO);

    @RequestMapping("/deletePackInfoById")
    RestResponse deletePackInfoById(@RequestParam("id") String id);
}
