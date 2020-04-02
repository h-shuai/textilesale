package com.rosellete.textilesale.controller;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.RoleBusiness;
import com.rosellete.textilesale.interfaces.RoleApi;
import com.rosellete.textilesale.model.RoleInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.RoleInfoVO;
import com.rosellete.textilesale.vo.RolePowerVO;
import com.rosellete.textilesale.vo.SetPowerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
@Slf4j
public class RoleController implements RoleApi {

    @Autowired
    private RoleBusiness roleBusiness;

    @Override
    public RestResponse getRoleList(@RequestBody RoleInfo roleInfo) {
        PageInfo<RolePowerVO> userList =  roleBusiness.getRoleList(roleInfo);
        return new RestResponse(userList);
    }

    @Override
    public RestResponse queryRole() {
        return roleBusiness.queryRole();
    }

    @Override
    public RestResponse saveRole(@RequestBody @Valid RoleInfoVO roleInfoVO) {
        return roleBusiness.saveRole(roleInfoVO);
    }

    @Override
    public RestResponse updateRole(@RequestBody @Valid RoleInfoVO roleInfoVO) {
        return roleBusiness.updateRole(roleInfoVO);
    }

    @Override
    public RestResponse updateStatus(String id) {
        return roleBusiness.updateStatus(id);
    }

    @Override
    public RestResponse setPower(@RequestBody @Valid SetPowerVO setPowerVO) {
        return roleBusiness.setPower(setPowerVO);
    }
}
