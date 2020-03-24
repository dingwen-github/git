
package net.qqxh.common.validate.enums;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Constants {
    public static final String RETURN = "return";
    public static final String DELIVER = "deliver";
    public static final String CODE_REPLACE = "#code";
    public static final String MSG_REPLACE = "#msg";
    public static final String PARAM_REPLACE = "#param";
    public static final String VAL_REPLACE = "#val";
    public static final String DEFAULT_TEMPLETE = "{paramName:'#param',value:'#val',code:'#code',message:'#msg'}";
    /**
     * 邮箱
     */
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 中文
     */
    public static final String CHINESE = "^[\\u4e00-\\u9fa5]{0,}$";
    /**
     * 数字
     */
    public static final String NUMBER = "^[0-9]*$";
    /**
     * 手机号
     */
    public static final String PHONE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    /**
     * 手机号
     */
    public static final String IDCARD = "^\\d{15}|\\d{18}$";
    /**
     * 邮编
     */
    public static final String POSTCODE = "^[0-9]{6}$";
    public static final String URL = "[a-zA-z]+://[^\\s]*";
    public static final String ACCEPTIMG = "[.](jpg|gif|bmp|png)$";
    public static final String ACCEPTFILE = "[.](xls|xlsx|csv|txt)$";

}