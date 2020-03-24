package net.qqxh.sunflow.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 用于方法上的日志注解
 *
 * @author jwy
 * @Retention是java当中的一个元注解，该元注解通常都是用于对软件的测试
 * @fileName: SfLog
 * @date: 23/05/2019 14:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String value() default "";

    /**
     * 是否入库标记，默认不保存入数据
     *
     * @throws
     * @Author jwy
     * @date 24/05/2019 15:58
     */
    boolean storage() default false;
}
