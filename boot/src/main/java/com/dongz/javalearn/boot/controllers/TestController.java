package com.dongz.javalearn.boot.controllers;


import com.dongz.javalearn.boot.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dong
 * @date 2019/12/23 17:47
 * @desc
 */
@RestController(value = "/api")
public class TestController {

    @Autowired
    private TestService service;

    @GetMapping("/test")
    public Long test() {
        return service.test(2L);
    }
}
