package com.imooc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {
    final static Logger logger = LoggerFactory.getLogger(AddressController.class);
    @GetMapping("/hello")
    public Object hello(){
        logger.info("info:6666");
        logger.debug("info:debug");
        logger.warn("info:warn");
        logger.error("info:error");
        return "hell world";
    }
}
