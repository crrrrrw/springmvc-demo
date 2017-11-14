package com.crw.springmvc.springmvcdemo.global;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 对所有注解 @Controller 和 @RestController 适用
 */
@ControllerAdvice(annotations = {Controller.class, RestController.class})
// @RestControllerAdvice //注解了@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView myException(Exception exception, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");// error页面
        modelAndView.addObject("errorCode", -1);
        modelAndView.addObject("errorMsg", exception.getMessage());
        return modelAndView;
    }

    @ModelAttribute
    public void ExtraMsg(Model model) {
        model.addAttribute("extraMsg", "额外信息");
    }

    /**
     * 定制 webDataBinder
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

}
