package com.imooc.controller;

import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车相关的api控制器",tags = "购物车相关的api")
@RequestMapping("shopcart")
@RestController
public class ShopcatController {


    @ApiOperation(value = "添加商品到购物车",notes = "添加商品到购物车",httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestParam String userId,
                               @RequestBody ShopcartBO shopcartBO,
                               HttpServletRequest request,
                               HttpServletResponse response
    ){
        // 判断权限，加入redis

        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg("userId不能为空");

        }
        System.out.println(shopcartBO.toString());
        // TODO 前端用户在登录的情况下添加商品到购物车，同步到redis缓存
        return IMOOCJSONResult.ok();
    }


    @ApiOperation(value = "从购物车中删除商品",notes = "从购物车中删除商品",httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(@RequestParam String userId,
                               @RequestParam String itemSpecId,
                               HttpServletRequest request,
                               HttpServletResponse response
    ){
        // 判断权限，加入redis

        if (StringUtils.isBlank(itemSpecId) || StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg("itemSpecId");

        }
        // TODO 前端用户在登录的情况下添加商品到购物车，同步到redis缓存
        return IMOOCJSONResult.ok();
    }
}
