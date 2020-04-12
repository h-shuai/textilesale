package com.rosellete.textilesale.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.UserBusiness;
import com.rosellete.textilesale.model.UserInfo;
import com.rosellete.textilesale.model.UserLinkRole;
import com.rosellete.textilesale.service.UserService;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.UserInfoVO;
import com.rosellete.textilesale.vo.UserLinkRoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UserBusinessImpl implements UserBusiness {

    @Autowired
    private UserService userService;

    @Override
    public PageInfo<UserInfoVO> getUserList(UserInfo userInfo) {
        PageHelper.startPage(userInfo.getPageNum(), userInfo.getPageSize());
        return new PageInfo<>(userService.getUserList(userInfo.getDepartId(),userInfo.getName(),userInfo.getPhone(),userInfo.getStatus()));
    }

    @Override
    public RestResponse getManager() {
        return new RestResponse(userService.getManager());
    }

    @Override
    public RestResponse saveUser(UserInfoVO userInfoVO) {
        RestResponse x = verifiparam(userInfoVO,1);
        if (x != null) return x;
        userInfoVO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        userInfoVO.setCreateTime(new Date());
        userInfoVO.setCreateUser("");
        userInfoVO.setStatus(1);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfo,userInfoVO);
        userService.saveUser(userInfo);
        UserLinkRoleVO userLinkRoleVO = new UserLinkRoleVO();
        userLinkRoleVO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        userLinkRoleVO.setUserId(userInfoVO.getId());
        userLinkRoleVO.setRoleId(userInfoVO.getRoleId());
        userLinkRoleVO.setStatus(1);
        userLinkRoleVO.setCreateTime(new Date());
        userLinkRoleVO.setCreateUser("");
        UserLinkRole userLinkRole = new UserLinkRole();
        BeanUtils.copyProperties(userLinkRole,userLinkRoleVO);
        userService.saveUserRole(userLinkRole);
        return new RestResponse();
    }

    private RestResponse verifiparam(UserInfoVO userInfoVO,int type) {
        if(type ==1){
            if(StringUtils.isEmpty( userInfoVO.getDepartId()) ||
                    StringUtils.isEmpty( userInfoVO.getAccount())){
                return new RestResponse(400,"参数错误");
            }
            int count1 = userService.getUserByAccount(userInfoVO.getAccount());
            if(count1>0 ){
                return new RestResponse(401,"用户已存在");
            }
            int count2 = userService.getUserByPhone(userInfoVO.getPhone());
            if(count2>0 ){
                return new RestResponse(402,"手机号码已存在");
            }
            int count = userService.userRole(userInfoVO.getId(),userInfoVO.getRoleId());
            if(count>0){
                return new RestResponse(403,"用户角色已存在");
            }
        }
        return null;
    }

    @Override
    @Transactional
    public RestResponse updateUser(UserInfoVO userInfoVO) {
        userInfoVO.setUpdateTime(new Date());
        userInfoVO.setUpdateUser("");
        UserInfo userInfo = userService.getUserDetails(userInfoVO.getId());
        String phone = userInfoVO.getPhone();
        if(!userInfo.getPhone().equals(phone)){
            //如果修改手机号,那么需要校验手机号是否已经存在
            int count = userService.getUserByPhone(phone);
            if(count>0){
                return new RestResponse(500,"手机号码已存在！");
            }
        }
        BeanUtils.copyProperties(userInfo,userInfoVO);
        userService.updateUser(userInfo);
        UserLinkRoleVO userLinkRoleVO = new UserLinkRoleVO();
        userLinkRoleVO.setUserId(userInfoVO.getId());
        userLinkRoleVO.setRoleId(userInfoVO.getRoleId());
        userLinkRoleVO.setUpdateTime(new Date());
        userLinkRoleVO.setUpdateUser("");
        UserLinkRole userLinkRole = new UserLinkRole();
        BeanUtils.copyProperties(userLinkRole,userLinkRoleVO);
        userService.updateUserRole(userLinkRole);
        return new RestResponse();
    }

    @Override
    public RestResponse getUserDetails(String userId) {
        UserInfoVO userInfo =  userService.getUserDetails(userId);
        return new RestResponse(userInfo);
    }

    @Override
    public RestResponse updateStatus(Map param) {
        return new RestResponse(userService.updateStatus(param));
    }

    @Override
    public UserInfo getUserById(String id) {
        return userService.getUserById(id);
    }

    @Override
    public RestResponse login(Map param) {
        Map<String,Object> result = userService.login(param);
        return new RestResponse(Integer.parseInt(result.get("statusCode").toString()),result.get("msgText").toString(),result.get("data"));
    }
}
