package com.dongz.hrm.common.controllers;

import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.entities.ResultCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dong
 * @date 2020/2/24 00:22
 * @desc
 */
@RestController
@RequestMapping("/api/error")
public class ErrorController {

    @RequestMapping("/authorError")
    public Result authorError(int code) {
        return code == 1 ? new Result(ResultCode.UNAUTHENTICATED) : new Result(ResultCode.UNAUTHORISE);
    }
}
