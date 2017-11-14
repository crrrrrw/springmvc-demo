package com.crw.springmvc.springmvcdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdviceController {

    @RequestMapping("/advice")
    public String advice(@ModelAttribute("extraMsg") String extraMsg) {
        throw new IllegalArgumentException("参数有误，额外信息:" + extraMsg);
    }

}
