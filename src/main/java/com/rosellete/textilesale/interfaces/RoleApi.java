package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.model.RoleInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.RoleInfoVO;
import com.rosellete.textilesale.vo.SetPowerVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface RoleApi {

    @GetMapping(value = "/getList",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getRoleList(@RequestBody RoleInfo roleInfo);

    @GetMapping("/queryRole")
    RestResponse queryRole();

    @PostMapping(value = "/saveRole",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveRole(@RequestBody @Valid RoleInfoVO roleInfoVO);

    @PostMapping(value = "/updateRole",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse updateRole(@RequestBody @Valid RoleInfoVO roleInfoVO);

    @PostMapping("/updateStatus")
    RestResponse updateStatus(@RequestParam(value = "id",required = true) String id);

    @PostMapping(value = "/setPower",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse setPower(@RequestBody @Valid SetPowerVO setPowerVO);
}
