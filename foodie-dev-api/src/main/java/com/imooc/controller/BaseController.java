package com.imooc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class BaseController {
   public  static final Integer COMMENT_PAGE_SIZE = 10;
   public  static final Integer PAGE_SIZE = 20;
}
