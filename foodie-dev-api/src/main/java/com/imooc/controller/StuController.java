package com.imooc.controller;

import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StuController {

    @Autowired
    private StuService StuService;

    @GetMapping("/getstuinfo")
    public Object getstuinfo(int id){
        return StuService.getStuInfo(id);
    }
}
