package com.crw.springmvc.springmvcdemo.controller;

import com.crw.springmvc.springmvcdemo.converter.MyMsgObj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConverterController {

    @ResponseBody
    @RequestMapping(value = "/converter", produces = "application/x-crw")
    public MyMsgObj converter(@RequestBody MyMsgObj myMsgObj) {
        myMsgObj.setMsg("process->" + myMsgObj.getMsg());
        return myMsgObj;
    }

}
