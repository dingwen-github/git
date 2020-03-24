package net.qqxh.sunflow.server.jedis;

/**
 * Copyright (C), 2019/6/22, sunflow开发团队
 * 〈缓存key枚举类〉<br>
 * 〈常量名称必须大写，常量值必须小写，常量值和名称必须除了大小写之外完全相等，并且只能用下划线分割（规范强制执行）〉
 *
 * @author jason
 * @fileName: JedisPrefix.java
 * @date: 2019/6/22 11:21
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class JedisPrefix {
    /**
     * key:登录名
     */
    public static final String USER_INFO = "user_info";
    public static final String USER_ROLES = "user_roles";
    public static final String DICTIONARY = "dictionary";

}
