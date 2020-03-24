package net.qqxh.sunflow.logging.core;

import net.qqxh.sunflow.logging.annotation.Log;
import net.qqxh.sunflow.logging.bean.SfLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Copyright (C), 2019/6/26, sunflow开发团队
 * 〈日志切面〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: AbstractLogAspect.java
 * @date: 2019/6/26 14:08
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class AbstractLogAspect {
    LoggingCallback loggingCallback;
    public final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(net.qqxh.sunflow.logging.annotation.Log)")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object info = loggingCallback.callback(joinPoint, getLog(joinPoint));
        LOG.info("<== {}", info);
        Object result = joinPoint.proceed();
        LOG.debug("==> {}", result);
        return result;
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Object info = loggingCallback.errorCallback(joinPoint, e, getLog(joinPoint));
        LOG.error("<== {}", info);
    }

    public void setLoggingCallback(LoggingCallback loggingCallback) {
        this.loggingCallback = loggingCallback;
    }

    public SfLog getLog(JoinPoint joinPoint) {
        SfLog sfLog = new SfLog();
        /*类名*/
        String className = joinPoint.getTarget().getClass().getName();
        /*方法名*/
        String methodName = joinPoint.getSignature().getName();
        sfLog.setMethod(className + "." + methodName + "()");
        sfLog.setTime(new Date());
        try {

            sfLog.setParams(getParams(joinPoint));
            Log log = getAnnotationLog(joinPoint);
            sfLog.setDescription(log.value());
            sfLog.setStorage(log.storage());
        } catch (Exception e) {
            LOG.error("getLog error", e);
        }
        return sfLog;
    }

    /**
     * 获取方法中的注解信息
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    private String getParams(JoinPoint joinPoint) {
        String params = "{";
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 用户名
        String username = "";

        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                params += " " + argNames[i] + ": " + argValues[i];
            }
        }
        return params;
    }
}
