package com.imooc.controller;

import com.imooc.pojo.bo.UserBO;
import com.imooc.service.StuService;
import com.imooc.service.UserService;
import com.imooc.utils.IMOOCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
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
    public IMOOCJSONResult regist(@RequestBody UserBO userBO){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)){
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }
        if (password.length() < 6){
            return IMOOCJSONResult.errorMsg("密码长度不能小于6a");

        }
        if (password.equals(confirmPwd)){
            return IMOOCJSONResult.errorMsg("两次密码不一样");
        }
        boolean isExist = UserService.queryUsernameIsExist(username);
        if (isExist){
            return IMOOCJSONResult.errorMsg("用户已经存在");
        }
        UserService.createUser(userBO);
        return  IMOOCJSONResult.ok();
    }
}
