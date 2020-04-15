package com.rosellete.textilesale.business;

import com.github.pagehelper.PageInfo;
import com.rosellete.textilesale.model.UserInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.UserInfoVO;

import java.util.Map;

public interface UserBusiness {
    PageInfo<UserInfoVO> getUserList(UserInfo userInfo);

    RestResponse getManager();

    RestResponse saveUser(UserInfoVO userInfoVO);

    RestResponse updateUser(UserInfoVO userInfoVO);

    RestResponse getUserDetails(String userId);

    RestResponse updateStatus(Map param);

    UserInfo getUserById(String id);

    RestResponse login(Map param);

    RestResponse info(String token);
}
