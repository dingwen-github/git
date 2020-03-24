
package net.qqxh.common.validate.enums;

/**
 * 〈校验模式〉<br>
 * 〈spring mvc模式rsf模式等等〉
 *
 * @author jason
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Model {
    /**
     * spring rsf 模式
     */
    public static final String RSF_MAP = "rsf_map";
    public static final String RSF_JSON = "rsf_json";

    /**
     * spring mvc ajax模式
     */
    public static final String MVC = "mvc";
    /**
     * spring mvc html视图模式
     */
    public static final String MVC_HTML = "mvc_html";

    /**
     * spring mvc html视图模式
     */
    public static final String ARGS = "args";
}