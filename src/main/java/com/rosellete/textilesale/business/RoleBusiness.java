package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.model.RoleInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.RoleInfoVO;
import com.rosellete.textilesale.vo.RolePowerVO;
import com.rosellete.textilesale.vo.SetPowerVO;

public interface RoleBusiness {
    PageInfo<RolePowerVO> getRoleList(RoleInfo roleInfo);

    RestResponse queryRole();

    RestResponse saveRole(RoleInfoVO roleInfoVO);

    RestResponse updateRole(RoleInfoVO roleInfoVO);

    RoleInfoVO getRoleDetails(String id);

    RestResponse updateStatus(String id);

    RestResponse setPower(SetPowerVO setPowerVO);
}
