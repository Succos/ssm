package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.StuService;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private UserService UserService;

    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username){
        if (StringUtils.isBlank(username)){
            return IMOOCJSONResult.errorMsg("用名不能为空");
        }
        boolean isExist = UserService.queryUsernameIsExist(username);

        if (isExist){
            return  IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        return IMOOCJSONResult.ok();
    }
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO,HttpServletRequest request,HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)){
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }
        if (password.length() < 6){
            return IMOOCJSONResult.errorMsg("密码长度不能小于6a");

        }
        if (!password.equals(confirmPwd)){
            return IMOOCJSONResult.errorMsg("两次密码不一样");
        }
        boolean isExist = UserService.queryUsernameIsExist(username);
        if (isExist){
            return IMOOCJSONResult.errorMsg("用户已经存在");
        }
        Users usersResult =  UserService.createUser(userBO);
        usersResult = setNullProperty(usersResult);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(usersResult),true);

        // TODO （注册）生成用户token,存入redis会话， 假如在其他地方登录过，现在换一台电脑登录
        // TODO  ，就要同步购物车的redis缓存

        return  IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
    HttpServletRequest request, HttpServletResponse response
                                 ) throws Exception{
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        if (StringUtils.isBlank(username)){
            return IMOOCJSONResult.errorMsg("用名不能为空");
        }
        if (StringUtils.isBlank(password)){
            return IMOOCJSONResult.errorMsg("密码不能为空");
        }
        Users usersResult = UserService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if(usersResult == null){
            return IMOOCJSONResult.errorMsg("用户密码不正确");
        }
        usersResult = setNullProperty(usersResult);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(usersResult),true);

        // TODO 生成用户token,存入redis会话， 假如在其他地方登录过，现在换一台电脑登录
        // TODO  ，就要同步购物车的redis缓存

        return IMOOCJSONResult.ok(usersResult);
    }
    private Users setNullProperty(Users usersResult){
        usersResult.setPassword(null);
        usersResult.setCreatedTime(null);
        usersResult.setEmail(null);
        usersResult.setMobile(null);
        usersResult.setRealname(null);
        return usersResult;
    }

}
