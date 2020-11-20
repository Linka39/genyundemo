package com.linka39.genyundemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根目录Controller
 * @author linka39
 */
@Controller
public class IndexController {

    /**
     * 根目录请求
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("index");
        mav.addObject("title","首页");
        return mav;
    }

    /**
     * 登录请求
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "/login";
    }

    /**
     * 进入后台管理请求
     * @return
     */
    @RequestMapping("/admin")
    public String toAdmin(){
        return "/admin/main";
    }

    /**
     * 去往欢迎画面
     * @return
     * @throws Exception
     */
    @RequestMapping("/admin/towelcom")
    public String towelcom(){
        return "/admin/welcom";
    }
}
