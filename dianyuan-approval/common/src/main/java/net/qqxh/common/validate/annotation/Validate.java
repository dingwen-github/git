package net.qqxh.common.validate.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright (C), 2002-2019,第七班
 * 〈入参校验注解〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @fileName: Validate.java
 * @date: 2019/6/26 13:54
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
    /*
     * 功能描述: <br>
     * 〈校验配置〉
     *
     * @return
     * @throws
     * @Author jason
     * @date 2019/6/27 12:58
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */

    String rules();

    /**
     * 功能描述: <br>
     * 〈校验模式〉
     *
     * @return java.lang.String
     * @throws
     * @Author jason
     * @date 2019/6/27 11:59
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String model() default "";

    /**
     * 功能描述: <br>
     * 〈支持配置从配置中心获取，配置的是整个rules〉
     *
     * @return java.lang.String
     * @throws
     * @Author jason
     * @date 2019/6/28 13:13
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String dynamicKey() default "";

    /**
     * 功能描述: <br>
     * 〈支持配置从scm获取，配置的是整个rules〉
     *
     * @return java.lang.String
     * @throws
     * @Author jason
     * @date 2019/6/28 13:13
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String msgTemp() default "";
}
