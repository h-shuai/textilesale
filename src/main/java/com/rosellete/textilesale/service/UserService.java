package com.rosellete.textilesale.service;

import com.alibaba.fastjson.JSONObject;
import com.rosellete.textilesale.dao.OrganizationDao;
import com.rosellete.textilesale.dao.PowerDao;
import com.rosellete.textilesale.dao.UserDao;
import com.rosellete.textilesale.dao.UserRoleDao;
import com.rosellete.textilesale.model.Organization;
import com.rosellete.textilesale.model.UserInfo;
import com.rosellete.textilesale.model.UserLinkRole;
import com.rosellete.textilesale.vo.LoginReqVO;
import com.rosellete.textilesale.vo.RoleVO;
import com.rosellete.textilesale.vo.UserInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    /** 成功状态码 */
    private static int SUCCESS_CODE = 20000;
    /** 错误、失败状态码 */
    private static int ERROR_CODE = 500;
    /** 返回状态键 */
    private static String STATUS_KEY = "statusCode";
    /** 消息键 */
    private static String MSG_KEY = "msgText";
    /** 返回主数据 */
    private static String MAIN_DATA = "data";
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private PowerDao powerDao;

    public List<UserInfoVO> getUserList(String departId, String name, String phone, Integer status) {
        List<Map<String,Object>> maps = userDao.getUserList(departId,name,phone,status);
        List<UserInfoVO> list = new ArrayList<>();
        for (Map<String,Object> map : maps){
            UserInfoVO userInfoVO = JSONObject.parseObject(JSONObject.toJSONString(map),UserInfoVO.class);
            list.add(userInfoVO);
        }
        return list;
    }

    public List<Map<String,Object>> getManager() {
        return userDao.getManager();
    }

    public int saveUser(UserInfo userInfo) {
        userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()).toLowerCase());
        UserInfo result = userDao.save(userInfo);
        return result == null ? 0 : 1;
    }
    public int saveUserRole(UserLinkRole userLinkRole) {
        UserLinkRole result = userRoleDao.save(userLinkRole);
        return result == null ? 0 : 1;
    }

    public int updateUser(UserInfo userInfo) {
        UserInfo result = userDao.save(userInfo);
        return result == null ? 0 : 1;
    }
    public int updateUserRole(UserLinkRole userLinkRole) {
        return userRoleDao.updateUserRole(userLinkRole);
    }
    public int userRole(String userId,String roleId) {
        return userRoleDao.userRole(userId,roleId);
    }
    public UserInfoVO getUserDetails(String userId) {
        UserInfo userInfo = userDao.getUserDetails(userId);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo,userInfoVO);
        return userInfoVO;
    }

    public int getUserByAccount(String accout) {
        return userDao.getUserByAccount(accout);
    }
    public int getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }
    public int getUserByCard(String card) {
        return userDao.getUserByCard(card);
    }

    public int updateStatus(Map param) {
        return userDao.updateStatus(Integer.valueOf(param.get("status").toString()),(String)param.get("id"));
    }

    public UserInfo getUserById(String id) {
        return userDao.getUserById(id);
    }

    public List<UserInfoVO> getUsersByRoleId(String roleId) {
        List<UserInfo> list = userDao.getUsersByRoleId(roleId);
        List<UserInfoVO> returnList = new ArrayList<>();
        for (UserInfo userInfo : list){
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtils.copyProperties(userInfoVO,userInfo);
            returnList.add(userInfoVO);
        }
        return returnList;
    }

    public UserInfoVO getUserDetailsByAccount(String account){
        UserInfo userInfo = userDao.getUserDetailsByAccount(account);
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfoVO,userInfo);
        return userInfoVO;
    }

    public Map<String, Object> login(Map param) {
        // 新建用于组装返回数据的map
        Map<String, Object> result = new HashMap<String, Object>();
        // 设置默认返回状态为失败
        result.put(STATUS_KEY, ERROR_CODE);
        UserInfo userInfo = null;
        if (null == param.get("username")) {
            result.put(MSG_KEY, "用户名或密码错误！");// 用户名不能为空
            return result;
        }

        if (null == param.get("password")) {
            result.put(MSG_KEY, "用户名或密码错误！");// 密码不能为空
            return result;
        }

        String userName = param.get("username").toString().trim();
        String password = param.get("password").toString().trim();

        if (StringUtils.isEmpty(userName)) {
            result.put(MSG_KEY, "用户名或密码错误！");// 用户名不能为空
            return result;
        }

        if (StringUtils.isEmpty(password)) {
            result.put(MSG_KEY, "用户名或密码错误！");// 密码不能为空
            return result;
        }

        if (password.length() < 6) {
            result.put(MSG_KEY, "密码长度不能小于6！");
            return result;
        }

        userInfo = userDao.getLoginUserByAccount(userName, DigestUtils.md5DigestAsHex(password.getBytes()).toLowerCase());

        if (null == userInfo) { // 账号不存在
            result.put(MSG_KEY, "用户名或密码错误！");
            return result;
        }

        if ("0".equals(userInfo.getStatus())) { // 账号不存在
            result.put(MSG_KEY, "该号码已禁用，请联系系统管理员！");
            return result;
        }
        String departId = userInfo.getDepartId();
        Organization organization = organizationDao.getOrganizaInfoById(departId);
        List<Organization> organizations = organizationDao.getAllOrgList();
        List<Organization> resultList = new ArrayList<>();
        if (null != organization.getParentId()) {
            resultList = getOrgList(organization.getParentId(), organizations);
        }
        resultList.add(organization);
        for (Organization vo : resultList) {
            if (null != vo.getStatus() && 0 == vo.getStatus()) {
                result.put(MSG_KEY, "部门已被禁用，请联系管理员！");
                return result;
            }
        }
        LoginReqVO data = new LoginReqVO();
        data.setUsername(userInfo.getName());
        data.setToken(userInfo.getId());

        result.put(MAIN_DATA, data);
        result.put(STATUS_KEY, SUCCESS_CODE);
        result.put(MSG_KEY, "登录成功！");
        return result;
    }

    /**
     * 通过机构id查找上级所以机构
     *
     * @param parentId
     * @param datas
     * @return
     */
    private List<Organization> getOrgList(String parentId, List<Organization> datas) {
        List<Organization> resultList = new ArrayList<>();
        for (Organization info : datas) {
            if (parentId.equals(info.getId())) {
                resultList.add(info);// 添加直接上级
                if (null != info.getParentId()) {
                    List<Organization> mapList = getOrgList(info.getParentId(), datas);
                    if (mapList.size() > 0) {
                        resultList.addAll(mapList);// 递归添加上级的上级
                    }
                }
            }
        }
        return resultList;
    }

    public RoleVO info(String token){
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add(userRoleDao.getRole(token));
        List<String> premList = powerDao.getPowerList(token);
        RoleVO roleVO = new RoleVO();
        roleVO.setRoles(roleIdList);
        roleVO.setPrems(premList);
        return roleVO;
    }
}
