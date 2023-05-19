package com.makhalin.springboot_homework.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingServiceLayer {

    @Around(value = "com.makhalin.springboot_homework.aop.CommonPointcuts.isServiceLayer() && " +
            "com.makhalin.springboot_homework.aop.CommonPointcuts.anyFindByIdServiceMethod() && " +
            "target(service) && " +
            "args(id)",
            argNames = "joinPoint,service,id")
    public Object addLoggingAroundFindByIdServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object id)
            throws Throwable {
        String methodName = "findById";
        log.info("before - invoked method {} in class {}, with id {}", methodName, service, id);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around("com.makhalin.springboot_homework.aop.CommonPointcuts.isServiceLayer() && " +
            "com.makhalin.springboot_homework.aop.CommonPointcuts.anyFindAllServiceMethod() && " +
            "target(service)")
    public Object addLoggingAroundFindAllServiceMethod(ProceedingJoinPoint joinPoint, Object service)
            throws Throwable {
        String methodName = "findAll";
        log.info("before - invoked method {} in class {}", methodName, service);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around(value = "com.makhalin.springboot_homework.aop.CommonPointcuts.isServiceLayer() && " +
            "com.makhalin.springboot_homework.aop.CommonPointcuts.anyFindAllByFilterServiceMethod() && " +
            "target(service) &&" +
            "args(filter, pageable)",
            argNames = "joinPoint,service,filter,pageable")
    public Object addLoggingAroundFindAllByFilterServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object filter, Object pageable)
            throws Throwable {
        String methodName = "findAllByFilter";
        log.info("before - invoked method {} in class {}, with filter {} and pageable {}", methodName, service, filter, pageable);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around(value = "com.makhalin.springboot_homework.aop.CommonPointcuts.isServiceLayer() && " +
            "com.makhalin.springboot_homework.aop.CommonPointcuts.anyFindAllByServiceMethod() && " +
            "target(service) && " +
            "args(id)",
            argNames = "joinPoint,service,id")
    public Object addLoggingAroundFindAllByServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object id)
            throws Throwable {
        String methodName = "findAllBy";
        log.info("before - invoked method {} in class {}, with id {}", methodName, service, id);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around(value = "com.makhalin.springboot_homework.aop.CommonPointcuts.isServiceLayer() && " +
            "com.makhalin.springboot_homework.aop.CommonPointcuts.anyCreateServiceMethod() && " +
            "target(service) && " +
            "args(arg)",
            argNames = "joinPoint,service,arg")
    public Object addLoggingAroundCreateServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object arg)
            throws Throwable {
        String methodName = "create";
        log.info("before - invoked method {} in class {}, with arg {}", methodName, service, arg);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around(value = "com.makhalin.springboot_homework.aop.CommonPointcuts.isServiceLayer() && " +
            "com.makhalin.springboot_homework.aop.CommonPointcuts.anyUpdateServiceMethod() && " +
            "target(service) && " +
            "args(id, arg)",
            argNames = "joinPoint,service,id,arg")
    public Object addLoggingAroundUpdateServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object id, Object arg)
            throws Throwable {
        String methodName = "update";
        log.info("before - invoked method {} in class {}, with id {} and arg {}", methodName, service, id, arg);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    @Around(value = "com.makhalin.springboot_homework.aop.CommonPointcuts.isServiceLayer() && " +
            "com.makhalin.springboot_homework.aop.CommonPointcuts.anyDeleteServiceMethod() && " +
            "target(service) && " +
            "args(id)",
            argNames = "joinPoint,service,id")
    public Object addLoggingAroundDeleteServiceMethod(ProceedingJoinPoint joinPoint, Object service, Object id)
            throws Throwable {
        String methodName = "delete";
        log.info("before - invoked method {} in class {}, with id {}", methodName, service, id);
        return addTryCatchBlock(joinPoint, service, methodName);
    }

    private Object addTryCatchBlock(ProceedingJoinPoint joinPoint, Object service, String methodName)
            throws Throwable {
        try {
            Object result = joinPoint.proceed();
            log.info("after returning - invoked method {} in class {}, result {}", methodName, service, result);
            return result;
        } catch (Throwable ex) {
            log.info("after throwing - invoked method {} in class {}, exception {}: {}",
                    methodName, service, ex.getClass(), ex.getMessage());
            throw ex;
        } finally {
            log.info("after (finally) - invoked method {} in class {}", methodName, service);
        }
    }
}
