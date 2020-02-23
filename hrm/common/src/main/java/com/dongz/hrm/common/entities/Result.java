package com.dongz.hrm.common.entities;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Result {

    private boolean success;//是否成功
    private Integer code;// 返回码
    private String message;//返回信息
    private Object data;// 返回数据

    public Result(ResultCode code) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
    }

    public Result(ResultCode code,Object data) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
        this.data = data;
    }

    public Result(Integer code,String message,boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static Result SUCCESS(){
        return new Result(ResultCode.SUCCESS);
    }

    public static Result SUCCESS(Object data){
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result LOGINSUCCESS(String token){
        return new Result(ResultCode.LOGINSUCCESS, token);
    }
    public static Result LOGINFAILE(String message){
        return new Result(ResultCode.LOGINFAILE, message);
    }

    public static Result ERROR(){
        return new Result(ResultCode.SERVER_ERROR);
    }

    public static Result FAILE(){

        return new Result(ResultCode.FAILE);
    }

    public static Result FAILE(Integer code, String msg) {
        return new Result(code, msg, false);
    }
}
