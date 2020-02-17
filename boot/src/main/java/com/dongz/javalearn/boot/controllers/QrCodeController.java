package com.dongz.javalearn.boot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dong
 * @date 2020/2/17 11:19
 * @desc
 */
@RestController
@RequestMapping
public class QrCodeController {


    @RequestMapping("/")
    public String test() {
        return "1231";
    }
}
