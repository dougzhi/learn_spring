package com.dongz.hrm.common.handlers;

import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.entities.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author dong
 * @date 2020/2/20 20:26
 * @desc
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Result> handlerException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(Result.FAILE(ResultCode.FAILE.code(), e.getMessage()), HttpStatus.OK);
    }
}
