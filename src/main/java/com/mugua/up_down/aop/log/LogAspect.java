package com.mugua.up_down.aop.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.Signature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.mugua.up_down.*.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        //debug日志
        log.debug("方法Before执行.....");
        //签名
        Signature signature = joinPoint.getSignature();
        //方法所在类类名
        log.debug("name:" + signature.getDeclaringTypeName());
        //方法名
        log.debug("signature.toString:" + signature.toString());
        //参数名和参数值
        Object[] args = joinPoint.getArgs();
        for (Object o: args){
            log.debug("object:arg:" + o.getClass().getSimpleName() + ",toString:" + o.toString());
        }

    }


    @After("webLog()")
    public void deAfter(JoinPoint joinPoint) throws Throwable {
        log.debug("方法After执行.....");
        //签名
        Signature signature = joinPoint.getSignature();
        //方法所在类类名
        log.debug("name:" + signature.getDeclaringTypeName());
        //方法名
        log.debug("signature.toString:" + signature.toString());
    }

    @AfterReturning(returning="returnValue", pointcut="webLog()")
    public void deReturn(JoinPoint joinPoint, Object returnValue) throws Throwable {
        log.debug("方法returnning执行.....");
        //签名
        Signature signature = joinPoint.getSignature();
        //方法所在类类名
        log.debug("name:" + signature.getDeclaringTypeName());
        //方法名
        log.debug("signature.toString:" + signature.toString());
        //返回值
        log.debug("return:" + returnValue);

    }



    //后置异常通知
    @AfterThrowing(value = "webLog()", throwing = "ex")
    public void throwss(JoinPoint joinPoint, Exception ex){
        log.error("方法异常时执行.....");
        //签名
        Signature signature = joinPoint.getSignature();
        //方法所在类类名
        log.error("name:" + signature.getDeclaringTypeName());
        //方法名
        log.error("signature.toString:" + signature.toString());
        log.error("异常信息:" + ex.getMessage());

    }

}
