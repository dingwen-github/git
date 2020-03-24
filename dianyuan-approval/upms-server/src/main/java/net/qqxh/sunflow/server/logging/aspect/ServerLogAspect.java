package net.qqxh.sunflow.server.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import net.qqxh.sunflow.logging.core.AbstractLogAspect;
import net.qqxh.sunflow.logging.core.LoggingCallback;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * <p>
 * 〈注册@LOG注解到业务系统，使其支持注解式日志〉<br>
 *
 * @author jwy
 * @fileName: ServerLogAspect
 * @date: 23/05/2019 15:08
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
@Aspect
@Slf4j
public class ServerLogAspect extends AbstractLogAspect {
    public ServerLogAspect(LoggingCallback loggingCallback) {
        super.setLoggingCallback(loggingCallback);
    }
}
