package com.dongz.javalearn.boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dong
 * @date 2020/2/17 11:19
 * @desc
 */
@Controller
@RequestMapping("/")
public class QrCodeController {

    @RequestMapping("/")
    public String test() {
        return "index";
    }

    @RequestMapping("/submit")
    public String submit(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        String agent = header.toLowerCase();
        if (agent.indexOf("micromessenger")>0) {
            return "wechat";
        }else if(agent.indexOf("alipayclient")>0){
            return "alipay";
        }
        return null;
    }
}
