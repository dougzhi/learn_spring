package com.dongz.javalearn.boot.annotation;

import java.lang.annotation.*;

/**
 * @author dong
 * @date 2019/12/23 17:43
 * @desc
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String id() default "";
}
