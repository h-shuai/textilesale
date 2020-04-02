package com.rosellete.textilesale.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.RoleBusiness;
import com.rosellete.textilesale.model.PowerLinkRole;
import com.rosellete.textilesale.model.RoleInfo;
import com.rosellete.textilesale.service.PowerService;
import com.rosellete.textilesale.service.RoleService;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.RoleInfoVO;
import com.rosellete.textilesale.vo.RolePowerVO;
import com.rosellete.textilesale.vo.SetPowerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class RoleBusinessImpl implements RoleBusiness {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PowerService powerService;

    @Override
    public PageInfo<RolePowerVO> getRoleList(RoleInfo role) {
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("name",role.getName());
        parameter.put("code",role.getCode());
        PageHelper.startPage(role.getPageNum(), role.getPageSize());
        PageInfo pageInfo = new PageInfo(roleService.getRoleList(parameter));
        List<RoleInfo> roleList = pageInfo.getList();
        List<RolePowerVO> list = new ArrayList<>();
        for (RoleInfo roleInfo: roleList) {
            RolePowerVO rolePowerVO= new RolePowerVO();
            rolePowerVO.setPowerList(powerService.getRolePower(roleInfo.getId()));
            rolePowerVO.setId(roleInfo.getId());
            rolePowerVO.setCode(roleInfo.getCode());
            rolePowerVO.setName(roleInfo.getName());
            rolePowerVO.setStatus(roleInfo.getStatus());
            rolePowerVO.setDescription(roleInfo.getDescription());
            list.add(rolePowerVO);
        }
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public RestResponse queryRole() {
        return new RestResponse(roleService.getRoleList(null));
    }

    @Override
    public RestResponse saveRole(RoleInfoVO roleInfoVO) {
        int count = roleService.isRoleName(roleInfoVO.getName());
        if (count>0){
            return new RestResponse(500,"已经存在该角色");
        }
        roleInfoVO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        roleInfoVO.setName(roleInfoVO.getName());
        roleInfoVO.setCreateTime(new Date());
        roleInfoVO.setCreateUser("");
        roleInfoVO.setCode(code());
        roleInfoVO.setStatus(1);
        roleInfoVO.setDescription(roleInfoVO.getDescription());
        roleService.saveRole(roleInfoVO);
        return new RestResponse();
    }

    private String code() {
        String equipmentNo = roleService.getIdex();
        String equipmentType = "R_";
        String newEquipmentNo = "001";
        if (equipmentNo != null && !equipmentNo.isEmpty()) {
            int newEquipment = Integer.parseInt(equipmentNo) + 1;
            newEquipmentNo = String.format(equipmentType + "%03d", newEquipment);
        }
        return newEquipmentNo;
    }

    @Override
    public RestResponse updateRole(RoleInfoVO roleInfoVO) {
        String name = roleService.getRoleDetails(roleInfoVO.getId()).getName();
        if(!name.equals(roleInfoVO.getName())){
            int count = roleService.isRoleName(roleInfoVO.getName());
            if (count>0){
                return new RestResponse(500,"已经存在该角色");
            }
        }
        roleInfoVO.setName(roleInfoVO.getName());
        roleInfoVO.setUpdateTime(new Date());
        roleInfoVO.setUpdateUser("");
        roleInfoVO.setDescription(roleInfoVO.getDescription());
        roleService.updateRole(roleInfoVO);
        return new RestResponse();
    }

    @Override
    public RoleInfoVO getRoleDetails(String id) {
        return roleService.getRoleDetails(id);
    }

    @Override
    public RestResponse updateStatus(String id) {
        roleService.updateStatus(id);
        return new RestResponse();
    }

    @Override
    public RestResponse setPower(SetPowerVO setPowerVO) {
        String roleId = "0";
        if(setPowerVO.getRoleId()!=null){
            roleId = setPowerVO.getRoleId();
        }
        int delResult = powerService.delRolePower(roleId);
        log.info("角色设置权限删除, delResult={}",delResult);

        List<String> list1 = setPowerVO.getPowerList();//传进来的code
        PowerLinkRole powerLinkRole = new PowerLinkRole();

        for (String string1: list1) {
            powerLinkRole = new PowerLinkRole();
            String powerid = powerService.powerId(string1);
            powerLinkRole.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            powerLinkRole.setCreateTime(new Date());
            powerLinkRole.setStatus(1);
            powerLinkRole.setCreateUser(setPowerVO.getUserId());
            powerLinkRole.setPowerId(powerid);
            powerLinkRole.setRoleId(roleId);
            powerService.savePower(powerLinkRole);
        }
        return new RestResponse();
    }
}
