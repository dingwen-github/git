package net.qqxh.common.validate.core;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface DynamicServer {
    /**
     * 功能描述: 获取配置值 <br>
     * 〈功能详细描述〉
     *
     * @param key
     * @param defaultValue 默认值，配置值为空，则返回默认值
     * @return
     * @author 14050181
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String getValue(String key, String defaultValue);
}