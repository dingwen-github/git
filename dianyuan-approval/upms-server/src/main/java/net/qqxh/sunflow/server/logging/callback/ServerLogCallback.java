package net.qqxh.sunflow.server.logging.callback;

import net.qqxh.sunflow.logging.bean.SfLog;
import net.qqxh.sunflow.logging.core.LoggingCallback;
import net.qqxh.sunflow.logging.service.LogService;
import net.qqxh.sunflow.server.shiro.core.ShiroUser;
import net.qqxh.sunflow.utils.ThrowableUtil;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈注解式日志记录回调函数〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: ServerLogCallback.java
 * @date: 2019/5/29 20:36
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
public class ServerLogCallback implements LoggingCallback {
    @Autowired
    private LogService logService;

    @Override
    public Object callback(ProceedingJoinPoint joinPoint, SfLog sfLog) {
        sfLog.setLogType(Level.INFO.toString());
        sfLog.setOptSystem("upms");
        String ip = SecurityUtils.getSubject().getSession().getHost();
        ShiroUser jfUserSimple = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        sfLog.setRequestIp(ip);
        if (jfUserSimple != null) {
            sfLog.setUsername(jfUserSimple.getLoginName());
        }
        if (sfLog.isStorage()) {
            //根据用户注解确定是否入库
            logService.save(sfLog);
        }
        return sfLog;
    }

    @Override
    public Object errorCallback(JoinPoint joinPoint, Throwable e, SfLog sfLog) {
        sfLog.setLogType(Level.ERROR.toString());
        sfLog.setOptSystem("upms");
        sfLog.setExceptionDetail(ThrowableUtil.getStackTrace(e));
        logService.save(sfLog);
        return sfLog;
    }
}
