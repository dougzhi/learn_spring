package com.dongz.hrm.common.exceptions;

/**
 * @author dong
 * @date 2020/2/27 14:46
 * @desc
 */
public class PrincipalIdNullException extends RuntimeException  {

    private static final String MESSAGE = "Principal Id shouldn't be null!";

    public PrincipalIdNullException(Class clazz, String idMethodName) {
        super(clazz + " id field: " +  idMethodName + ", value is null\n" + MESSAGE);
    }
}