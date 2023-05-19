package com.makhalin.springboot_homework.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcuts {

    @Pointcut("within(com.makhalin.*.service.*Service)")
    public void isServiceLayer() {
    }

    @Pointcut("execution(public * findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    @Pointcut("execution(public * findAll())")
    public void anyFindAllServiceMethod() {
    }

    @Pointcut("execution(public * findAll(*,*))")
    public void anyFindAllByFilterServiceMethod() {
    }

    @Pointcut("execution(public * findAllBy*(*))")
    public void anyFindAllByServiceMethod() {
    }

    @Pointcut("execution(public * create(*))")
    public void anyCreateServiceMethod() {
    }

    @Pointcut("execution(public * update(*,*))")
    public void anyUpdateServiceMethod() {
    }

    @Pointcut("execution(public * delete(*))")
    public void anyDeleteServiceMethod() {
    }
}
