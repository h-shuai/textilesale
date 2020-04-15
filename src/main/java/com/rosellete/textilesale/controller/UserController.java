package com.rosellete.textilesale.controller;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.business.UserBusiness;
import com.rosellete.textilesale.interfaces.UserApi;
import com.rosellete.textilesale.model.UserInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.LoginReqVO;
import com.rosellete.textilesale.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController implements UserApi {

    @Autowired
    private UserBusiness userBusiness;

    @Override
    public RestResponse getUserList(@RequestBody UserInfo userInfo) {
        PageInfo<UserInfoVO> userList =  userBusiness.getUserList(userInfo);
        return new RestResponse(userList);
    }

    @Override
    public RestResponse getManager() {
        return userBusiness.getManager();
    }

    @Override
    public RestResponse saveUser(@RequestBody @Valid UserInfoVO userInfoVO) {
        return userBusiness.saveUser(userInfoVO);
    }

    @Override
    public RestResponse updateUser(@RequestBody @Valid UserInfoVO userInfoVO) {
        return userBusiness.updateUser(userInfoVO);
    }

    @Override
    public RestResponse getUserDetails(@PathVariable String id) {
        return userBusiness.getUserDetails(id);
    }

    @Override
    public RestResponse updateStatus(@RequestBody @Valid Map param) {
        return userBusiness.updateStatus(param);
    }

    @Override
    public UserInfo getUserById(String id) {
        return userBusiness.getUserById(id);
    }

    @Override
    public RestResponse login(@RequestBody Map param) {
        return userBusiness.login(param);
    }

    @Override
    public RestResponse info(String token) {
        return userBusiness.info(token);
    }

    @Override
    public RestResponse logout() {
        return new RestResponse();
    }
}
