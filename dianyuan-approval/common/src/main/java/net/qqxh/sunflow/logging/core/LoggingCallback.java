package net.qqxh.sunflow.logging.core;

import net.qqxh.sunflow.logging.bean.SfLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public interface LoggingCallback {
    Object callback(ProceedingJoinPoint joinPoint, SfLog sfLog);
    Object errorCallback(JoinPoint joinPoint, Throwable e,SfLog sfLog);
}
