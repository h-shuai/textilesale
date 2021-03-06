package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.model.UserInfo;
import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

public interface UserApi {
    @PostMapping(value = "/getList",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse getUserList(@RequestBody UserInfo userInfo);

    @GetMapping("/getManager")
    RestResponse getManager();

    @PostMapping(value = "/saveUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse saveUser(@RequestBody @Valid UserInfoVO userInfoVO);

    @PostMapping(value = "/updateUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse updateUser(@RequestBody @Valid UserInfoVO userInfoVO);

    @GetMapping("/getUserDetails/{id}")
    RestResponse getUserDetails(@PathVariable("id") String id);

    @PostMapping(value = "/updateStatus",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse updateStatus(@RequestBody @Valid Map param);

    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse login(@RequestBody Map param);

    @RequestMapping("/info")
    RestResponse info(@Param("token") String token);

    @PostMapping("/logout")
    RestResponse logout();
}
