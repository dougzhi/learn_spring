package com.dongz.javalearn.boot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author dong
 * @date 2019/12/23 19:27
 * @desc
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(com.dongz.javalearn.boot.annotation.Log)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕通知开始");
        return joinPoint.proceed();
    }
}
