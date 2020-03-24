package net.qqxh.common.validate.enums;

/**
 * Copyright (C), 2002-2019,第七班
 * 〈validate类型〉<br>
 * 〈目前已支持四种后面会继续扩展更多高端炫酷的校验逻辑〉
 *
 * @author jason
 * @fileName: ValidatorType.java
 * @date: 2019/6/27 14:40
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ValidatorType {
    /**
     * 用不到的，用于防止空指针问题
     */
    public static final String DEFAULT = "default";
    /**
     * @Validate(rules = "loginName=required:{max:1,min:2,msg:'请输入用户名',code:500};") max为最大长度 min为最小长度msg为校验失败提醒
     */
    public static final String REQUIRED = "required";
    /**
     * @Validate(rules = "count=number:{max:1,min:20,msg:'请输入1-20之间的数字',code:500};") max为最大值 min为最小值msg为校验失败提醒
     */
    public static final String INT = "int";
    public static final String LONG = "long";
    public static final String DOUBLE = "double";
    /**
     * @Validate(rules = "date=date:{fmt:'yyyyMMdd',msg:'时间格式不正确',code:500};") fmt时间格式,msg为校验失败提醒
     */
    public static final String DATE = "date";
    /**
     * @Validate(rules = "paramname=regex:{reg:'正则表达式',msg:'入参格式不正确',code:500};") reg正则表达式,msg为校验失败提醒
     */
    public static final String REGEX = "regex";
    public static final String IN = "in";
    public static final String LIKE = "like";
}
